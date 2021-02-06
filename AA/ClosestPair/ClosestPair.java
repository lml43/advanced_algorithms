package ClosestPair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClosestPair {

    static List<Point> P = Arrays.asList(
            new Point(1, 2),
            new Point(23,63),
            new Point(52, 16),
            new Point(43, 3),
            new Point(82, 36),
            new Point(55, 99),
            new Point(50, 98),
            new Point(10, 20),
            new Point(21, 52),
            new Point(97, 98),
            new Point(28, 57),
            new Point(53, 88),
            new Point(51, 61),
            new Point(63, 67),
            new Point(91, 24),
            new Point(31, 35),
            new Point(73,83)
    );

    public static void main(String[] args) {

        List<Point> X = P.stream()
                .sorted(Comparator.comparingInt(p -> p.x))
                .collect(Collectors.toList());

        List<Point> Y = P.stream()
                .sorted(Comparator.comparingInt(p -> p.x))
                .collect(Collectors.toList());

        PairPoint pairPoint = findMinDistance(X, Y);
        System.out.println();
        System.out.println(pairPoint);
    }

    private static PairPoint findMinDistance(List<Point> X, List<Point> Y) {

        System.out.println();
        System.out.println("###### New Call ######");
        X.forEach(System.out::println);

        if (X.size() <= 3) {
            return bruteForce(X);
        }

        int lIdx = (int) Math.ceil(X.size() / 2f);
        int lX = (X.get(lIdx-1).x + X.get(lIdx).x)/2;


        List<Point> xLeft = X.subList(0, lIdx);
        List<Point> xRight = X.subList(lIdx, X.size());


        List<Point> yLeft = xLeft.stream()
                .sorted(Comparator.comparingInt(p->p.y))
                .collect(Collectors.toList());
        List<Point> yRight = xLeft.stream()
                .sorted(Comparator.comparingInt(p->p.y))
                .collect(Collectors.toList());

        PairPoint pairLeft = findMinDistance(xLeft, yLeft);
        PairPoint pairRight = findMinDistance(xRight, yRight);

        PairPoint conquerRes = pairLeft.distance < pairRight.distance ? pairLeft : pairRight;

        List<Point> yPrime = Y.stream()
                .filter(point -> (point.x - lX) <= conquerRes.distance)
                .sorted(Comparator.comparingInt(p->p.y))
                .collect(Collectors.toList());

        double minDis = Double.MAX_VALUE;
        PairPoint resPrime = null;

        for (int i = 0; i < yPrime.size()-1; i++) {
            for (int j = i+1; j <= i+9 && j < yPrime.size(); j++) {
                double dis = distance(yPrime.get(i), yPrime.get(j));
                if (dis < minDis) {
                    minDis = dis;
                    resPrime = new PairPoint(dis, yPrime.get(i), yPrime.get(j));
                }
            }
        }

        return resPrime.distance < conquerRes.distance ? resPrime : conquerRes;
    }

    private static PairPoint bruteForce(List<Point> x) {
        System.out.println();
        System.out.println("---Brute Force---");

        x.forEach(System.out::println);
        double minDis = Double.MAX_VALUE;
        PairPoint res = null;

        for (int i = 0; i < x.size(); i++) {
            for (int j = i+1; j < x.size(); j++) {
                double dis = distance(x.get(i), x.get(j));
                if (dis < minDis) {
                    minDis = dis;
                    res = new PairPoint(dis, x.get(i), x.get(j));
                }
            }
        }

        System.out.println();
        System.out.println("Result: " + res);

        return res;
    }

    private static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y-b.y, 2));
    }

    private static class PairPoint {
        double distance;
        Point p1;
        Point p2;

        public PairPoint(double distance, Point p1, Point p2) {
            this.distance = distance;
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public String toString() {
            return "PairPoint{\n" +
                    "distance=" + distance +
                    ",\np1=" + p1 +
                    ",\np2=" + p2 +
                    "\n}";
        }
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{"+ x +
                    "," + y +
                    '}';
        }
    }


}
