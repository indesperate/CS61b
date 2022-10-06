package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

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
        WorldGenerator worldGenerator = null;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = StdDraw.nextKeyTyped();
            if (Character.toLowerCase(c) == 'n') {
                break;
            }
            if (Character.toLowerCase(c) == 'l') {
                worldGenerator = Util.loadWorld();
                break;
            }
            if (Character.toLowerCase(c) == 'q') {
                System.exit(0);
                return;
            }
        }
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        if (worldGenerator == null) {
            worldGenerator = new WorldGenerator(WIDTH, HEIGHT, gameUI.getSeed());
        }
        ter.initialize(WIDTH, HEIGHT);
        worldGenerator.drawWorld(world);
        ter.renderFrame(world);
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = Character.toLowerCase(StdDraw.nextKeyTyped());
            switch (c) {
                case 'w' : {
                    worldGenerator.playerMove(world, Util.Direction.NORTH);
                    break;
                }
                case 'a' : {
                    worldGenerator.playerMove(world, Util.Direction.WEST);
                    break;
                }
                case 'd' : {
                    worldGenerator.playerMove(world, Util.Direction.EAST);
                    break;
                }
                case 's' : {
                    worldGenerator.playerMove(world, Util.Direction.SOUTH);
                    break;
                }
                case 'q' : {
                    Util.saveWorld(worldGenerator);
                    System.exit(0);
                    break;
                }
                default:
                    break;
            }
            ter.renderFrame(world);
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
        long seed = 0;
        try {
            StringReader reader = new StringReader(input.toLowerCase());
            while (true) {
                if (reader.read() == 'n') {
                    break;
                }
            }
            while (true) {
                int c = reader.read();
                if (c == 's') {
                    break;
                }
                seed = (c - '0')  + seed * 10;
            }
            reader.close();
        } catch (java.io.IOException e) {
            throw new RuntimeException("Error during startup of service", e);
        }
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        WorldGenerator worldGenerator = new WorldGenerator(WIDTH, HEIGHT, seed);
        worldGenerator.drawWorld(world);
        return world;
    }
}
