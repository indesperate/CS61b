package byog.Core;

import byog.TileEngine.TETile;

import java.awt.Point;

public class util {
    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }
    public static void drawLine(TETile[][] tiles, Point point, int length, Direction direction, TETile type) {
        switch (direction) {
            case NORTH -> {
                for (int i = 0; i < length; i += 1) {
                    tiles[point.x][point.y + i] = type;
                }
            }
            case EAST -> {
                for (int i = 0; i < length; i += 1) {
                    tiles[point.x + i][point.y] = type;
                }
            }
            case SOUTH -> {
                for (int i = 0; i < length; i += 1) {
                    tiles[point.x][point.y - i] = type;
                }
            }
            case WEST -> {
                for (int i = 0; i < length; i += 1) {
                    tiles[point.x - i][point.y] = type;
                }
            }
        }
    }

    public static void drawHollowRect(TETile[][] tiles, Point point, int width, int height, TETile type) {
        Point instancePoint = new Point(point);
        drawLine(tiles, instancePoint, height - 1, Direction.NORTH,  type);
        instancePoint.y += height - 1;
        drawLine(tiles, instancePoint, width - 1, Direction.EAST, type);
        instancePoint.x += width - 1;
        drawLine(tiles, instancePoint, height - 1, Direction.SOUTH, type);
        instancePoint.y -= height - 1;
        drawLine(tiles, instancePoint, width - 1, Direction.WEST, type);
    }

    public static void drawRect(TETile[][] tiles, Point point, int width, int height, TETile type) {
        Point instancePoint = new Point(point);
        for (int i = 0; i < height; i += 1) {
            drawLine(tiles, instancePoint, width, Direction.EAST, type);
            instancePoint.y += 1;
        }
    }
}
