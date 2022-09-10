package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int SEED = 11233;
    private static final Random RANDOM = new Random(SEED);
    /** the position begin from left */
    private static void drawHorizontalLine(TETile[][] tiles, Point point, int length, TETile type) {
        for (int i = 0; i < length; i += 1) {
            tiles[point.x + i][point.y] = type;
        }
    }
    private static void drawVerticalLine(TETile[][] tiles, Point point, int length, TETile type) {
        for (int i = 0; i < length; i += 1) {
            tiles[point.x][point.y + i] = type;
        }
    }

    /** the position is the leftest middle item position */
    private  static void drawHalfHexagon(TETile[][] tiles, Point point, int size, int direction, TETile type) {
        Point instancePoint = new Point(point);
        for (int i = 0; i < size; i += 1) {
            int length = size + 2 * (size - 1 - i);
            drawHorizontalLine(tiles, instancePoint, length, type);
            instancePoint.x += 1;
            instancePoint.y += direction;
        }
    }

    public static void addHexagon(TETile[][] tiles, Point point ,int size, TETile type) {
        Point instancePoint = new Point(point);
        drawHalfHexagon(tiles, instancePoint, size, -1, type);
        instancePoint.y += 1;
        drawHalfHexagon(tiles, instancePoint, size, 1, type);
    }

    /** return random TETile */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(7);
        return switch (tileNum) {
            case 0 -> Tileset.WALL;
            case 1 -> Tileset.FLOWER;
            case 2 -> Tileset.TREE;
            case 3 -> Tileset.GRASS;
            case 4 -> Tileset.MOUNTAIN;
            case 5 -> Tileset.WATER;
            case 6 -> Tileset.FLOOR;
            default -> Tileset.NOTHING;
        };
    }

    /** begin from the downside the move up */
    private static void addHexagonVerticalLine(TETile[][] tiles, Point point, int length, int size) {
        Point instancePoint = new Point(point);
        for (int i = 0; i < length; i += 1) {
            addHexagon(tiles, instancePoint, size, randomTile());
            instancePoint.y += size * 2;
        }
    }
    public static void addTesselationHexagons(TETile[][] tiles, Point pointG, int size) {
        Point point = new Point(pointG);
        addHexagonVerticalLine(tiles, point, 3, size);
        point.x += size * 2 - 1;
        point.y -= size;
        addHexagonVerticalLine(tiles, point, 4, size);
        point.x += size * 2 - 1;
        point.y -= size;
        addHexagonVerticalLine(tiles, point, 5, size);
        point.x += size * 2 - 1;
        point.y += size;
        addHexagonVerticalLine(tiles, point, 4, size);
        point.x += size * 2 - 1;
        point.y += size;
        addHexagonVerticalLine(tiles, point, 3, size);
    }

    private static TETile[][] initializeTETile() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        int size = 4;
        TETile[][] world = initializeTETile();
        Point point = new Point(0, HEIGHT / 2 - size * 2);
        addTesselationHexagons(world, point, size);
        ter.renderFrame(world);
    }
}
