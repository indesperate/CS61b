package byog.Core;

import byog.TileEngine.TETile;

import java.awt.*;
import java.util.Random;

public class Halfway {
    final util.Direction direction;
    final Room fRoom;
    final Room sRoom;

    final Random random;
    public Halfway(Random random, Room fRoom, Room sRoom, util.Direction direction) {
        this.direction = direction;
        this.fRoom = fRoom;
        this.sRoom = sRoom;
        this.random = random;
    }

    public void draw(TETile[][] world) {
    }
}
