package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public class Room {
    /**
     * the bottom left position of the room.
     */
    final Point position = new Point();
    int width;
    int height;
    Random random;

    /**
     * generator the room use a random number
     */
    public Room(Random random, int iWidth, int iHeight) {
        this.random = random;
        position.x = RandomUtils.uniform(random, iWidth - 3);
        position.y = RandomUtils.uniform(random, iHeight - 3);
        width = RandomUtils.uniform(random, 4, 10);
        height = RandomUtils.uniform(random, 4, 10);
        width = Math.min(width, iWidth - position.x);
        height = Math.min(height, iHeight - position.y);
    }

    /**
     * reinitialize
     */
    public void reInitialize(int iWidth, int iHeight) {
        position.x = RandomUtils.uniform(random, iWidth - 3);
        position.y = RandomUtils.uniform(random, iHeight - 3);
        width = RandomUtils.uniform(random, 4, 10);
        height = RandomUtils.uniform(random, 4, 10);
        width = Math.min(width, iWidth - position.x);
        height = Math.min(height, iHeight - position.y);
    }

    /**
     * check if conflict
     */
    public boolean checkConflict(List<Room> list) {
        for (Room room : list) {
            if (conflict(room)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if it conflicts with other room
     */
    public boolean conflict(Room other) {
        int dx = position.x - other.position.x;
        int dy = position.y - other.position.y;
        return dx < other.width && dx > -width && dy < other.height && dy > -height;
    }

    public void draw(TETile[][] world) {
        Util.drawHollowRect(world, position, width, height, Tileset.WALL);
        Point in = new Point(position.x + 1, position.y + 1);
        Util.drawRect(world, in, width - 2, height - 2, Tileset.FLOOR);
    }

    public Point generatePoint() {
        Point randomPoint = new Point();
        randomPoint.x = position.x + width / 2;
        randomPoint.y = position.y + height / 2;
        return randomPoint;
    }
}
