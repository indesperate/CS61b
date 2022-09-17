package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/** the class take a seed and generates the world. */
public class WorldGenerator {
    private final int WIDTH;
    private final int HEIGHT;
    private final Random RANDOM;
    private TETile[][] world;
    WorldGenerator(int width, int height, int seed) {
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
    public TETile[][] generateWorld() {
        List<Room> roomList = new LinkedList<>();
        for (int i = 0; i < 30; i ++) {
            Room room = new Room(RANDOM, WIDTH, HEIGHT);
            while (room.checkConflict(roomList)) {
                room.reInitialize(WIDTH, HEIGHT);
            }
            roomList.add(room);
        }
        for (Room room : roomList) {
            room.draw(world);
        }
        return world;
    }
}
