package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private final int[][] tiles;
    private final int N;
    private static final int BLANK = 0;
    private final int manhan;
    /**
     * Construct a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, colum j
     * @param tiles the input tiles
     */
    public Board(int[][] tiles) {
        int rowLength = tiles.length;
        int colLength = tiles[0].length;
        assert rowLength == colLength;
        N = rowLength;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, N);
        }
        manhan = manhattan();
    }

    /**
     * Return the value of tile at row i, column j (or 0 if blank)
     * @param i row
     * @param j colum
     * @return value
     */
    public int tileAt(int i, int j) {
        if (i >= N || i < 0 || j >= N || j < 0) {
            throw new java.lang.IllegalArgumentException("i or j must be less than N, bigger than zero");
        }
        return tiles[i][j];
    }

    /**
     * return the board size N
     * @return N
     */
    public int size() {
        return N;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhan;
    }

    /**
     * return the neighbors of the current board
     * cite from josh-hog
     * @return the neighbors
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                int goalNumAtIJ = i * N + j + 1;
                if (tileAt(i,j) != BLANK) {
                    hamming += Math.abs(tileAt(i, j) - goalNumAtIJ);
                }
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                int numAtIJ = tileAt(i, j);
                if (numAtIJ != BLANK) {
                    int row = (numAtIJ - 1) / N;
                    int col = (numAtIJ - 1) % N;
                    manhattan += (Math.abs(row - i) + Math.abs(col - j));
                }
            }
        }
        return manhattan;
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }
        Board board = (Board)y;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (board.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the string representation of the board.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
