package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int N;
    private final int top;
    private final int bottom;
    private final boolean[][] state;
    private final WeightedQuickUnionUF unionUF;
    private final WeightedQuickUnionUF unionUFIn;
    private int numOpen = 0;
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N must greater than zero");
        }
        state = new boolean[N][N];
        unionUF = new WeightedQuickUnionUF( N * N + 1);
        unionUFIn = new WeightedQuickUnionUF(N * N + 2);
        top = N * N;
        bottom = top + 1;
        this.N = N;
        for (int i = 0; i < N; i += 1) {
            unionUF.union(xyToUF(0, i), top);
            unionUFIn.union(xyToUF(0, i), top);
            unionUFIn.union(xyToUF(N - 1, i), bottom);
        }
    }

    private int xyToUF(int rol, int col) {
        return rol * N + col;
    }

    private void connectAround(WeightedQuickUnionUF unionUF,int row, int col) {
        if (row > 0 && isOpen(row - 1, col)) {
            unionUF.union(xyToUF(row, col), xyToUF(row - 1, col));
        }
        if (row < N - 1 && isOpen(row + 1, col)) {
            unionUF.union(xyToUF(row, col), xyToUF(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            unionUF.union(xyToUF(row, col), xyToUF(row, col - 1));
        }
        if (col < N - 1 && isOpen(row, col + 1)) {
            unionUF.union(xyToUF(row, col), xyToUF(row, col + 1));
        }
    }

    public void open(int row, int col) {
        if (row >= N || col >= N) {
            throw new java.lang.IndexOutOfBoundsException("out of index");
        }
        state[row][col] = true;
        numOpen += 1;
        connectAround(unionUF,row, col);
        if (!percolates()) {
            connectAround(unionUFIn, row, col);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row >= N || col >= N) {
            throw new java.lang.IndexOutOfBoundsException("out of index");
        }
        return state[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row >= N || col >= N) {
            throw new java.lang.IndexOutOfBoundsException("out of index");
        }
        if (!isOpen(row, col)) {
            return false;
        }
        return unionUF.connected(xyToUF(row, col), top);
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return unionUFIn.connected(top, bottom);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats percolationStats = new PercolationStats(20, 100, pf);
        System.out.println(percolationStats.stddev());
        System.out.println(percolationStats.mean());
        System.out.println(percolationStats.confidenceLow());
        System.out.println(percolationStats.confidenceHigh());
    }
}
