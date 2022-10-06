package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * the class take a seed and generates the world.
 */
public class WorldGenerator implements Serializable {
    private final int WIDTH;
    private final int HEIGHT;
    private final Random RANDOM;
    private Point door;
    private Point player;
    private List<Room> roomList;
    private List<Halfway> wayList;

    @Serial
    private static final long serialVersionUID = 82188832479874L;


    WorldGenerator(int width, int height, long seed) {
        WIDTH = width;
        HEIGHT = height;
        RANDOM = new Random(seed);
        generateWorld();
    }

    private void initializeTETile(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private int generateNum(int i, int size) {
        int num = i;
        while (i == num) {
            num = RandomUtils.uniform(RANDOM, 0, size);
        }
        return num;
    }

    private void generateWorld() {
        roomList = new ArrayList<>();
        for (int i = 0; i < RandomUtils.uniform(RANDOM, 10, 30); i++) {
            Room room = new Room(RANDOM, WIDTH, HEIGHT);
            while (room.checkConflict(roomList)) {
                room.reInitialize(WIDTH, HEIGHT);
            }
            roomList.add(room);
        }
        wayList = new ArrayList<>();
        int size = roomList.size();
        for (int i = 0; i < size; i += 1) {
            Room first = roomList.get(i);
            Room another = roomList.get(generateNum(i, size));
            Halfway way = new Halfway(RANDOM, first, another);
            wayList.add(way);
        }
        door = new Point(roomList.get(0).position);
        door.x += 1;
        player = roomList.get(1).generatePoint();
    }

    public void drawWorld(TETile[][] world) {
        initializeTETile(world);
        for (Room room : roomList) {
            room.draw(world);
        }
        for (Halfway halfway : wayList) {
            halfway.draw(world);
        }
        world[door.x][door.y] = Tileset.LOCKED_DOOR;
        world[player.x][player.y] = Tileset.PLAYER;
    }

    public void playerMove(TETile[][] world, Util.Direction direction) {
        world[player.x][player.y] = Tileset.FLOOR;
        switch (direction) {
            case NORTH: {
                if (world[player.x][player.y + 1].equals(Tileset.FLOOR)) {
                    player.y += 1;
                }
                break;
            }
            case EAST: {
                if (world[player.x + 1][player.y].equals(Tileset.FLOOR)) {
                    player.x += 1;
                }
                break;
            }
            case SOUTH: {
                if (world[player.x][player.y - 1].equals(Tileset.FLOOR)) {
                    player.y -= 1;
                }
                break;
            }
            case WEST: {
                if (world[player.x - 1][player.y].equals(Tileset.FLOOR)) {
                    player.x -= 1;
                }
            }
            default: {
                break;
            }
        }
        world[player.x][player.y] = Tileset.PLAYER;
    }
}
