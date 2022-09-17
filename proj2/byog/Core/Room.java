package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public class Room {
    /** the bottom left position of the room. */
    final Point position = new Point();
    int width;
    int height;
    Random random;
    /** generator the room use a random number */
    public Room(Random random, int WIDTH, int HEIGHT) {
        this.random = random;
        position.x = random.nextInt(WIDTH);
        position.y = random.nextInt(HEIGHT);
        width = random.nextInt(3, 9);
        height = random.nextInt(3, 9);
        width = Math.min(width, WIDTH - position.x);
        height = Math.min(height, HEIGHT - position.y);
    }
    /** reinitialize */
    public void reInitialize(int WIDTH, int HEIGHT) {
        position.x = random.nextInt(WIDTH - 3);
        position.y = random.nextInt(HEIGHT - 3);
        width = random.nextInt(3, 9);
        height = random.nextInt(3, 9);
        width = Math.min(width, WIDTH - position.x);
        height = Math.min(height, HEIGHT - position.y);
    }
    /** check if conflict */
    public boolean checkConflict(List<Room> list) {
        for (Room room : list) {
            if (conflict(room)) {
                return true;
            }
        }
        return false;
    }
    /** check if it conflicts with other room */
    public boolean conflict(Room other) {
        int dx = position.x - other.position.x;
        int dy = position.y - other.position.y;
        return dx < other.width && dx > -width && dy < other.height && dy > -height;
    }

    public void draw(TETile[][] world) {
        util.drawHollowRect(world, position, width, height, Tileset.WALL);
        Point in = new Point(position.x + 1, position.y + 1);
        util.drawRect(world, in, width - 2, height - 2, Tileset.FLOOR);
    }
}
