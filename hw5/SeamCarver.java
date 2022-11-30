import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        int xLeft = Math.floorMod(x - 1, width());
        int xRight = Math.floorMod(x + 1, width());
        int yUp = Math.floorMod(y - 1, height());
        int yDown = Math.floorMod(y + 1, height());
        return energyHelper(picture.get(xLeft, y), picture.get(xRight, y))
                + energyHelper(picture.get(x, yUp), picture.get(x, yDown));
    }

    private double energyHelper(Color first, Color second) {
        int R = first.getRed() - second.getRed();
        int G = first.getGreen() - second.getGreen();
        int B = first.getBlue() - second.getBlue();
        return Math.pow(R, 2) + Math.pow(G, 2) + Math.pow(B, 2);
    }

    public int[] findHorizontalSeam() {
        return  null;
    }

    public int[] findVerticalSeam() {
        double[][] M = new double[height()][width()];
        int[][] P = new int[height()][width()];
        for (int x = 0; x < width(); x += 1) {
            M[0][x] = energy(x, 0);
            P[0][x] = x;
        }
        for (int y = 1; y < height(); y += 1) {
            for (int x = 0; x < width(); x += 1) {
                calculateM(M, P, x, y);
            }
        }
        double min = Float.MAX_VALUE;
        int min_x = 0;
        for (int x = 0; x < width(); x += 1) {
            if (min > M[height() - 1][x]) {
                min = M[height() - 1][x];
                min_x = x;
            }
        }
        int[] result = new int[height()];
        for (int y = 0; y < height(); y += 1) {
            result[height() - y - 1] = min_x;
            min_x = P[height() - y - 1][min_x];
        }

        return result;
    }

    private void calculateM(double[][] M, int[][] P, int x, int y) {
        double left = getM(M, x - 1, y - 1);
        double mid = getM(M, x, y - 1);
        double right = getM(M, x + 1, y - 1);
        if (left < mid && left < right) {
            M[y][x] = left + energy(x, y);
            P[y][x] = x - 1;
        } else if (mid < left && mid < right) {
            M[y][x] = mid + energy(x, y);
            P[y][x] = x;
        } else {
            M[y][x] = right + energy(x, y);
            P[y][x] = x + 1;
        }
    }

    private double getM(double[][] M, int x, int y) {
        if (x < 0 || x > width() - 1) {
            return Float.MAX_VALUE;
        }
        return M[y][x];
    }
    public void removeHorizontalSeam(int[] seam) {

    }

    public void removeVerticalSeam(int[] seam) {

    }
}
