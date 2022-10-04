package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i += 1) {
            result.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return result.toString();
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        StdDraw.setPenColor();
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(this.width / 2., this.height / 2., s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        StdDraw.pause(500);
        for (int i = 0; i < letters.length(); i += 1) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(750);
            drawFrame(" ");
            StdDraw.pause(750);
        }
    }

    public String solicitNCharsInput(int n) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < n) {
            if (StdDraw.hasNextKeyTyped()) {
                result.append(StdDraw.nextKeyTyped());
                i += 1;
                drawFrame(result.toString());
            }
        }
        StdDraw.pause(500);
        return result.toString();
    }

    public void startGame() {
        round = 1;
        gameOver = false;
        playerTurn = false;
        while (!gameOver) {
            drawFrame("Round: " + round);
            String secret = generateRandomString(round);
            flashSequence(secret);
            drawFrame("Please type");
            playerTurn  = true;
            String typeIn = solicitNCharsInput(round);
            if (!secret.equals(typeIn)) {
                gameOver = true;
                drawFrame("Game Over");
            }
            else {
                drawFrame("Correct, well done!");
                StdDraw.pause(1500);
                round += 1;
            }
        }
    }

}
