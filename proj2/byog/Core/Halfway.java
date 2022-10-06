package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;
import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class Halfway implements Serializable {
    Point first;
    Point second;
    Boolean direction;

    @Serial
    private static final long serialVersionUID = 231239823474L;
    public Halfway(Random random, Room fRoom, Room sRoom) {
        if (fRoom.position.x > sRoom.position.x) {
            Room temp = fRoom;
            fRoom = sRoom;
            sRoom = temp;
        }
        first = fRoom.generatePoint();
        second = sRoom.generatePoint();
        direction = RandomUtils.bernoulli(random);
    }


    public void draw(TETile[][] world) {
        Util.drawLThreeLine(world, first, second, Tileset.FLOOR, Tileset.WALL, direction);
    }
}
