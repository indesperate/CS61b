package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Util {
    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }

    public static boolean check(TETile typeIn, TETile typeToDraw) {
        return !typeIn.equals(Tileset.FLOOR) || !typeToDraw.equals(Tileset.WALL);
    }

    public static void drawLine(TETile[][] tiles,
                                Point point,
                                int length,
                                Direction direction,
                                TETile type) {
        switch (direction) {
            case NORTH: {
                for (int i = 0; i < length; i += 1) {
                    if (check(tiles[point.x][point.y + i], type)) {
                        tiles[point.x][point.y + i] = type;
                    }
                }
                break;
            }
            case EAST: {
                for (int i = 0; i < length; i += 1) {
                    if (check(tiles[point.x + i][point.y], type)) {
                        tiles[point.x + i][point.y] = type;
                    }
                }
                break;
            }
            case SOUTH: {
                for (int i = 0; i < length; i += 1) {
                    if (check(tiles[point.x][point.y - i], type)) {
                        tiles[point.x][point.y - i] = type;
                    }
                }
                break;
            }
            case WEST: {
                for (int i = 0; i < length; i += 1) {
                    if (check(tiles[point.x - i][point.y], type)) {
                        tiles[point.x - i][point.y] = type;
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    public static void drawHollowRect(TETile[][] tiles,
                                      Point point,
                                      int width,
                                      int height,
                                      TETile type) {
        Point instancePoint = new Point(point);
        drawLine(tiles, instancePoint, height - 1, Direction.NORTH, type);
        instancePoint.y += height - 1;
        drawLine(tiles, instancePoint, width - 1, Direction.EAST, type);
        instancePoint.x += width - 1;
        drawLine(tiles, instancePoint, height - 1, Direction.SOUTH, type);
        instancePoint.y -= height - 1;
        drawLine(tiles, instancePoint, width - 1, Direction.WEST, type);
    }

    public static void drawRect(TETile[][] tiles,
                                Point point,
                                int width,
                                int height,
                                TETile type) {
        Point instancePoint = new Point(point);
        for (int i = 0; i < height; i += 1) {
            drawLine(tiles, instancePoint, width, Direction.EAST, type);
            instancePoint.y += 1;
        }
    }

    public static void drawLine(TETile[][] tiles,
                                Point first,
                                Point second,
                                TETile type) {
        assert first.x == second.x || first.y == second.y;
        if (first.equals(second)) {
            return;
        }
        if (first.x == second.x) {
            if (first.y < second.y) {
                drawLine(tiles, first, second.y - first.y + 1, Direction.NORTH, type);
            } else {
                drawLine(tiles, first, first.y - second.y + 1, Direction.SOUTH, type);
            }
        } else {
            if (first.x < second.x) {
                drawLine(tiles, first, second.x - first.x + 1, Direction.EAST, type);
            } else {
                drawLine(tiles, first, first.x - second.x + 1, Direction.WEST, type);
            }
        }
    }

    public static void drawThreeLine(TETile[][] tiles,
                                     Point first,
                                     Point second,
                                     TETile in,
                                     TETile wrap) {
        assert first.x == second.x || first.y == second.y;
        if (first.equals(second)) {
            return;
        }
        if (first.x == second.x) {
            Point lFirst = new Point(first.x - 1, first.y);
            Point rFirst = new Point(first.x + 1, first.y);
            if (first.y < second.y) {
                drawLine(tiles, lFirst, second.y - first.y + 1, Direction.NORTH, wrap);
                drawLine(tiles, first, second.y - first.y + 1, Direction.NORTH, in);
                drawLine(tiles, rFirst, second.y - first.y + 1, Direction.NORTH, wrap);
            } else {
                drawLine(tiles, lFirst, first.y - second.y + 1, Direction.SOUTH, wrap);
                drawLine(tiles, first, first.y - second.y + 1, Direction.SOUTH, in);
                drawLine(tiles, rFirst, first.y - second.y + 1, Direction.SOUTH, wrap);
            }
        } else {
            Point lFirst = new Point(first.x, first.y - 1);
            Point rFirst = new Point(first.x, first.y + 1);
            if (first.x < second.x) {
                drawLine(tiles, lFirst, second.x - first.x + 1, Direction.EAST, wrap);
                drawLine(tiles, first, second.x - first.x + 1, Direction.EAST, in);
                drawLine(tiles, rFirst, second.x - first.x + 1, Direction.EAST, wrap);
            } else {
                drawLine(tiles, lFirst, first.x - second.x + 1, Direction.WEST, wrap);
                drawLine(tiles, first, first.x - second.x + 1, Direction.WEST, in);
                drawLine(tiles, rFirst, first.x - second.x + 1, Direction.WEST, wrap);
            }

        }
    }

    public static void drawLThreeLinePart(TETile[][] tiles,
                                          Point first,
                                          Point second,
                                          TETile in,
                                          TETile wrap) {
        assert first.x == second.x || first.y == second.y;
        if (first.equals(second)) {
            return;
        }
        if (first.x == second.x) {
            Point lFirst = new Point(first.x - 1, first.y);
            Point rFirst = new Point(first.x + 1, first.y);
            if (first.y < second.y) {
                drawLine(tiles, lFirst, second.y - first.y + 2, Direction.NORTH, wrap);
                drawLine(tiles, first, second.y - first.y + 1, Direction.NORTH, in);
                drawLine(tiles, rFirst, second.y - first.y + 2, Direction.NORTH, wrap);
            } else {
                drawLine(tiles, lFirst, first.y - second.y + 2, Direction.SOUTH, wrap);
                drawLine(tiles, first, first.y - second.y + 1, Direction.SOUTH, in);
                drawLine(tiles, rFirst, first.y - second.y + 2, Direction.SOUTH, wrap);
            }
        } else {
            Point lFirst = new Point(first.x, first.y - 1);
            Point rFirst = new Point(first.x, first.y + 1);
            if (first.x < second.x) {
                drawLine(tiles, lFirst, second.x - first.x + 2, Direction.EAST, wrap);
                drawLine(tiles, first, second.x - first.x + 1, Direction.EAST, in);
                drawLine(tiles, rFirst, second.x - first.x + 2, Direction.EAST, wrap);
            } else {
                drawLine(tiles, lFirst, first.x - second.x + 2, Direction.WEST, wrap);
                drawLine(tiles, first, first.x - second.x + 1, Direction.WEST, in);
                drawLine(tiles, rFirst, first.x - second.x + 2, Direction.WEST, wrap);
            }

        }
    }

    public static void drawLThreeLine(TETile[][] tiles,
                                      Point first,
                                      Point second,
                                      TETile in,
                                      TETile wrap,
                                      boolean direction) {
        if (first.x == second.x || first.y == second.y) {
            drawThreeLine(tiles, first, second, in, wrap);
            return;
        }
        Point mid = new Point();
        if (direction) {
            mid.x = first.x;
            mid.y = second.y;
        } else {
            mid.x = second.x;
            mid.y = first.y;
        }
        drawLThreeLinePart(tiles, first, mid, in, wrap);
        drawLThreeLinePart(tiles, second, mid, in, wrap);
    }

    public static void saveWorld(WorldGenerator worldGenerator) {
        try {
            FileOutputStream fs = new FileOutputStream("save_file.txt");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(worldGenerator);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public static WorldGenerator loadWorld() {
        try {
            FileInputStream fs = new FileInputStream("save_file.txt");
            ObjectInputStream os = new ObjectInputStream(fs);
            WorldGenerator loadWorld = (WorldGenerator) os.readObject();
            os.close();
            return loadWorld;
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
            System.exit(0);
        }
        return null;
    }
}
