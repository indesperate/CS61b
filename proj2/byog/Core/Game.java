package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.IOException;
import java.io.StringReader;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        GameUI gameUI = new GameUI(WIDTH, HEIGHT);
        gameUI.drawUI();
        WorldGenerator worldGenerator = gameUI.begin();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        if (worldGenerator == null) {
            worldGenerator = new WorldGenerator(WIDTH, HEIGHT, gameUI.getSeed());
        }
        ter.initialize(WIDTH, HEIGHT + 2, 0, 0);
        worldGenerator.drawWorld(world);
        ter.renderFrame(world);
        TETile underMouse = Tileset.NOTHING;
        boolean flag = false;
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        while (true) {
            if (x != (int) StdDraw.mouseX() || y != (int) StdDraw.mouseY()) {
                x = (int) StdDraw.mouseX();
                y = (int) StdDraw.mouseY();
                if (x < WIDTH && y < HEIGHT) {
                    underMouse = world[x][y];
                }
                ter.renderFrame(world);
                gameUI.underMouse(underMouse);
            }
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = Character.toLowerCase(StdDraw.nextKeyTyped());
            switch (c) {
                case ':': {
                    flag = true;
                    break;
                }
                case 'q': {
                    if (flag) {
                        Util.saveWorld(worldGenerator);
                        System.exit(0);
                    }
                    break;
                }
                default: {
                    flag = false;
                    worldGenerator.playerMove(world, c);
                    ter.renderFrame(world);
                    gameUI.underMouse(underMouse);
                }
            }
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        StringReader reader = new StringReader(input.toLowerCase());
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        WorldGenerator worldGenerator = null;
        while (true) {
            int c = 0;
            try {
                c = reader.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (c == 'n') {
                break;
            }
            if (c == 'l') {
                worldGenerator = Util.loadWorld();
                break;
            }
            if (c == 'q') {
                System.exit(0);
            }
        }
        if (worldGenerator == null) {
            int seed = 0;
            while (true) {
                int c = 0;
                try {
                    c = reader.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (c == 's') {
                    break;
                }
                seed = (c - '0') + seed * 10;
            }
            worldGenerator = new WorldGenerator(WIDTH, HEIGHT, seed);
        }
        worldGenerator.drawWorld(world);
        boolean flag = false;
        while (true) {
            int c = 0;
            try {
                c = reader.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (c == -1) {
                return world;
            }
            switch (c) {
                case ':': {
                    flag = true;
                    break;
                }
                case 'q': {
                    if (flag) {
                        Util.saveWorld(worldGenerator);
                        return world;
                    }
                    break;
                }
                default: {
                    flag = false;
                    worldGenerator.playerMove(world, (char) c);
                }
            }
        }
    }
}
