package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * the class take a seed and generates the world.
 */
public class WorldGenerator {
    private final int WIDTH;
    private final int HEIGHT;
    private final Random RANDOM;
    private TETile[][] world;

    WorldGenerator(int width, int height, long seed) {
        WIDTH = width;
        HEIGHT = height;
        RANDOM = new Random(seed);
        initializeTETile();
    }

    private void initializeTETile() {
        world = new TETile[WIDTH][HEIGHT];
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

    public TETile[][] generateWorld() {
        List<Room> roomList = new ArrayList<>();
        for (int i = 0; i < RandomUtils.uniform(RANDOM, 10, 30); i++) {
            Room room = new Room(RANDOM, WIDTH, HEIGHT);
            while (room.checkConflict(roomList)) {
                room.reInitialize(WIDTH, HEIGHT);
            }
            roomList.add(room);
        }
        List<Halfway> wayList = new ArrayList<>();
        int size = roomList.size();
        for (int i = 0; i < size; i += 1) {
            Room first = roomList.get(i);
            Room another = roomList.get(generateNum(i, size));
            Halfway way = new Halfway(RANDOM, first, another);
            wayList.add(way);
        }
        for (Room room : roomList) {
            room.draw(world);
        }

        for (Halfway way : wayList) {
            way.draw(world);
        }

        Point door = roomList.get(0).position;
        world[door.x + 1][door.y] = Tileset.LOCKED_DOOR;
        return world;
    }
}
