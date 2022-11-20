
import javax.print.attribute.standard.MediaSize;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    private static class SearchNode implements Comparable<SearchNode> {
        private final long id;
        private SearchNode parent;
        private double distance;
        private final GraphDB graphDB;
        private final long dest;


        SearchNode(long id, long dest, GraphDB graphDB) {
            this.id = id;
            this.parent = null;
            this.dest = dest;
            this.distance = 0.0;
            this.graphDB = graphDB;
        }
        SearchNode(long id, long dest, SearchNode parent, GraphDB graphDB) {
            this.id = id;
            this.parent = parent;
            this.dest = dest;
            this.distance = parent.distance + graphDB.distance(id, parent.id);
            this.graphDB = graphDB;
        }

        @Override
        public int compareTo(SearchNode o) {
            double cmp = distance + graphDB.distance(id, dest) - o.distance - graphDB.distance(o.id, o.dest);
            return Double.compare(cmp, 0.0);
        }
    }
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        long start = g.closest(stlon, stlat);
        long dest = g.closest(destlon, destlat);
        HashMap<Long, SearchNode> marked = new HashMap<>();
        PriorityQueue<SearchNode> searchQueue = new PriorityQueue<>();
        SearchNode beginNode = new SearchNode(start, dest, g);
        searchQueue.add(beginNode);
        marked.put(start, beginNode);
        List<Long> path = new LinkedList<>();
        while (!searchQueue.isEmpty()) {
            SearchNode searchNode = searchQueue.poll();
            long p = searchNode.id;
            if (p == dest) {
                while (searchNode.parent != null) {
                    path.add(0, searchNode.id);
                    searchNode = searchNode.parent;
                }
                path.add(0, start);
                return path;
            }
            for (Long v : g.adjacent(p)) {
                if (marked.get(v) == null) {
                    SearchNode neighbour = new SearchNode(v, dest, searchNode, g);
                    searchQueue.add(neighbour);
                    marked.put(v, neighbour);
                } else {
                    SearchNode neighbour = new SearchNode(v, dest, searchNode, g);
                    SearchNode origin = marked.get(v);
                    if (neighbour.compareTo(origin) < 0) {
                        searchQueue.add(neighbour);
                        marked.replace(v, neighbour);
                    }
                }
            }
        }
        return path;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        Long first = route.get(0);
        Long sec = route.get(1);
        List<NavigationDirection> guide = new LinkedList<>();
        NavigationDirection node = new NavigationDirection();
        node.distance = 0.0;
        node.way = g.getPathName(first, sec);
        double previousDegree = g.bearing(first, sec);
        for (Long second : route) {
            if (Objects.equals(first, second)) {
                continue;
            }
            String currentWay = g.getPathName(first, second);
            double currentDistance = g.distance(first, second);
            double currentDegree = g.bearing(first, second);
            if (Objects.equals(currentWay, node.way)) {
                node.distance += currentDistance;
            } else {
                guide.add(node);
                node = new NavigationDirection();
                node.direction = calculateDirection(previousDegree, currentDegree);
                node.distance = currentDistance;
                node.way = currentWay;
            }
            previousDegree = currentDegree;
            first = second;
        }
        guide.add(node);
        guide.get(0).direction = NavigationDirection.START;
        return guide;
    }

    private static int calculateDirection(double first, double second) {
        double degrees = second - first;
        if (degrees < -180.) {
            degrees += 360.;
        }
        if (degrees > 180.) {
            degrees -= 360.;
        }
        if (degrees <= 15. && degrees >= -15.) {
            return NavigationDirection.STRAIGHT;
        } else if (degrees >= -30. && degrees <= -15.) {
            return NavigationDirection.SLIGHT_LEFT;
        } else if (degrees >= 15. && degrees <= 30) {
            return NavigationDirection.SLIGHT_RIGHT;
        } else if (degrees >= -100. && degrees <= -30.) {
            return NavigationDirection.LEFT;
        } else if (degrees >= 30. && degrees <= 100.) {
            return NavigationDirection.RIGHT;
        } else if (degrees <= -100) {
            return NavigationDirection.SHARP_LEFT;
        } else {
            return NavigationDirection.SHARP_RIGHT;
        }
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
