import java.util.*;

class AStar {

    public static int width;
    public static int height;
    public static Map<Point, Point> cameFrom = new HashMap<>();
    public static Map<Point, Integer> gX = new HashMap<>();
    public static Map<Point, Integer> fX = new HashMap<>();

    public static int minimumDistance(List<List<Integer>> area) {
        // Write your code here
        height = area.size();

        if (height == 0) {
            return 0;
        }

        width = area.get(0).size();

        Point start = new Point(0,0);
        Point goal = findDestination(area);
        Point current = start;

        Set<Point> availablePoints = new HashSet<>();
        List<Point> openSet = new ArrayList<>();

        gX.put(start, 0);
        fX.put(start, distance(start, goal));

        availablePoints.add(start);
        availablePoints.add(goal);
        openSet.add(start);

        while (openSet.size() != 0) {
            current = openSet.get(0);

            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current).size();
            }

            openSet.remove(0);

            Set<Point> neighbors = findNeighbors(current, area);
            for (Point neighbor : neighbors) {
                int tempGX = gX.get(current) + distance(current, neighbor);

                gX.putIfAbsent(neighbor, Integer.MAX_VALUE);
                if (tempGX < gX.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gX.put(neighbor, tempGX);
                    fX.put(neighbor, tempGX + distance(neighbor, goal));
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }

            Collections.sort(openSet);
        }

        return -1;
    }

    private static List<Point> reconstructPath(Map<Point, Point> cameFrom, Point current) {
        List<Point> finalPath = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            finalPath.add(current);
        }
        return finalPath;
    }

    private static Set<Point> findNeighbors(Point currentPoint, List<List<Integer>> area) {
        Set<Point> res = new HashSet<>();

        if (currentPoint.x - 1 >= 0 && area.get(currentPoint.y).get(currentPoint.x - 1) != 0) {
            Point newPoint = new Point(currentPoint.x - 1, currentPoint.y);
            res.add(newPoint);

        }

        if (currentPoint.x + 1 < area.get(currentPoint.y).size() && area.get(currentPoint.y).get(currentPoint.x + 1) != 0) {
            Point newPoint = new Point(currentPoint.x + 1, currentPoint.y);
            res.add(newPoint);
        }

        if (currentPoint.y - 1 >= 0 && area.get(currentPoint.y - 1).get(currentPoint.x) != 0) {
            Point newPoint = new Point(currentPoint.x, currentPoint.y - 1);
            res.add(newPoint);
        }

        if (currentPoint.y + 1 < area.size() && area.get(currentPoint.y + 1).get(currentPoint.x) != 0) {
            Point newPoint = new Point(currentPoint.x, currentPoint.y + 1);
            res.add(newPoint);
        }

        return res;
    }

    private static Point findDestination(List<List<Integer>> area) {
        for (int i = 0; i < area.size(); i++) {
            int index = area.get(i).indexOf(9);
            if (index != -1) {
                return new Point(i, index);
            }
        }
        return null;
    }

    private static int distance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }


    public static class Point implements Comparable<Point> {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }


        @Override
        public int compareTo(Point o) {
            return Integer.compare(fX.get(this), fX.get(o));
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> area = Arrays.asList(
                Arrays.asList(1,1,1,1,1,1,1),
                Arrays.asList(1,1,1,1,1,1,1),
                Arrays.asList(1,1,1,1,1,1,1),
                Arrays.asList(0,0,0,1,0,0,0),
                Arrays.asList(1,1,1,1,1,9,0),
                Arrays.asList(1,1,1,1,1,1,1)
        );


        System.out.println(minimumDistance(area));
    }


}