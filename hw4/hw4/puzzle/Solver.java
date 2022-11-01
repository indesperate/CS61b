package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;
import java.util.List;


public class Solver {
    /* the moves of the nearest path */
    int moves;
    /* the nearest path */
    List<WorldState> path;

    /* inner search node */
    private static class SearchNode implements Comparable<SearchNode> {
        WorldState worldState;
        int moves;
        SearchNode previous;

        SearchNode(WorldState worldState, int moves, SearchNode previous) {
            this.worldState = worldState;
            this.moves = moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.moves + this.worldState.estimatedDistanceToGoal() - o.moves - o.worldState.estimatedDistanceToGoal();
        }

    }

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution()
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     *
     * @param initial world initial stat.
     */
    public Solver(WorldState initial) {
        path = new LinkedList<>();
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(initial, 0, null));
        while (true) {
            SearchNode nearestNode = minPQ.delMin();
            if (nearestNode.worldState.isGoal()) {
                this.moves = nearestNode.moves;
                while (nearestNode != null) {
                    path.add(0, nearestNode.worldState);
                    nearestNode = nearestNode.previous;
                }
                break;
            }
            for (WorldState neighbour : nearestNode.worldState.neighbors()) {
                if (nearestNode.previous == null || !neighbour.equals(nearestNode.previous.worldState)) {
                    minPQ.insert(new SearchNode(neighbour, nearestNode.moves + 1, nearestNode));
                }
            }
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle
     * starting at the initial WorldState.
     *
     * @return the number of moves needed.
     */
    public int moves() {
        return moves;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution
     *
     * @return the path to the solution
     */
    public Iterable<WorldState> solution() {
        return path;
    }
}
