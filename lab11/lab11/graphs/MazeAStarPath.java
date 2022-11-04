package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import javax.swing.*;
import java.security.Provider;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private Maze maze;

    private class SearchNode implements Comparable<SearchNode> {
        private final int v;
        public SearchNode(int v) {
            this.v = v;
        }

        @Override
        public int compareTo(SearchNode o) {
            return distTo[v] + h(v) - distTo[o.v] - h(o.v);
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /**
     * Finds vertex estimated to be closest to target.
     */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /**
     * Performs an A star search from vertex s.
     */
    private void astar(int s) {
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(s));
        marked[s] = true;
        announce();
        while (!minPQ.isEmpty()) {
            int v = minPQ.delMin().v;
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    announce();
                    if (w == t) {
                        return;
                    }
                    minPQ.insert(new SearchNode(w));
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

