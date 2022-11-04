package lab11.graphs;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cycle;
    private boolean endDraw;
    private int cycleBegin;

    public MazeCycles(Maze m) {
        super(m);
        cycle = false;
        endDraw = false;
        cycleBegin = 0;
    }

    @Override
    public void solve() {
        dfsCycle(maze.xyTo1D(1, 1), maze.xyTo1D(1, 1));
    }

    // Helper methods go here
    private void dfsCycle(int v, int parent) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                dfsCycle(w, v);
                if (cycle) {
                    if (!endDraw) {
                        edgeTo[w] = v;
                        announce();
                    }
                    if (cycleBegin == v) {
                        endDraw = true;
                    }
                    return;
                }
            } else {
                if (parent != w) {
                    edgeTo[w] = v;
                    cycleBegin = w;
                    cycle = true;
                    announce();
                    return;
                }
            }
        }
    }
}

