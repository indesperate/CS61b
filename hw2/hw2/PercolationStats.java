package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private final double[] array;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("N must greater than zero");
        }
        array = new double[T];
        for (int i = 0; i < T; i += 1) {
            int open = 0;
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!percolation.isOpen(row, col)) {
                    open += 1;
                    percolation.open(row, col);
                }
            }
            array[i] = 1. * open / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(array);
    }

    public double stddev() {
        return StdStats.stddev(array);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(array.length);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(array.length);
    }
}
