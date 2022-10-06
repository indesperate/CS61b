package byog.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Color;


public class GameUI {
    private final int width;
    private final int height;
    private final Font bigFont = new Font("Monaco", Font.BOLD, 30);
    private final Font smallFont = new Font("Monaco", Font.BOLD, 20);

    public GameUI(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawUI() {
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(this.width / 2., this.height / 4. * 3, "CS61B: THE GAME");
        StdDraw.setFont(smallFont);
        StdDraw.text(this.width / 2., this.height / 2., "Load Game (L)");
        StdDraw.text(this.width / 2., this.height / 2. + 1.2, "New Game (N)");
        StdDraw.text(this.width / 2., this.height / 2. - 1.2, "Quit (Q)");
        StdDraw.show();
    }

    public void drawSeed(String num) {
        StdDraw.clear();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(smallFont);
        StdDraw.text(this.width / 2., this.height / 4., "seed:" + num);
        StdDraw.show();
    }

    public int getSeed() {
        StringBuilder seed = new StringBuilder();
        drawSeed(seed.toString());
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = StdDraw.nextKeyTyped();
            if (Character.toLowerCase(c) == 's') {
                break;
            }
            if (Character.isDigit(c)) {
                seed.append(c);
                drawSeed(seed.toString());
            }
        }
        if (seed.length() == 0) {
            seed.append('0');
        }
        return Integer.parseInt(seed.toString());
    }
}
