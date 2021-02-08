package GreedySetCover;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GreedySetCover {

    static Set<Integer> X;
    static List<Set<Integer>> F;

    private static void init() {
        X = Stream.of(
                1,2,3,4,5,6,7,8,9,10,11,12,13
        ).collect(Collectors.toSet());

        Set<Integer> s1 = Stream.of(1,2).collect(Collectors.toSet());
        Set<Integer> s2 = Stream.of(2,3,4,5).collect(Collectors.toSet());
        Set<Integer> s3 = Stream.of(6,7,8,9,10,11,12,13).collect(Collectors.toSet());
        Set<Integer> s4 = Stream.of(1,3,5,7,9,11,13).collect(Collectors.toSet());
        Set<Integer> s5 = Stream.of(2,4,6,8,10,12,13).collect(Collectors.toSet());

        F = Stream.of(s1, s2, s3, s4, s5).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        init();

        Set<Integer> U = new HashSet<>(X);
        List<Set<Integer>> A = new ArrayList<>();

        while (U.size() > 0) {
            System.out.println("---------------------------");
            Set<Integer> S = selectMaxSet(U);
            A.add(S);
            System.out.print("A:(");
            A.forEach(s -> {
                printSet(s);
                System.out.print(", ");
            });
            System.out.print(")");
            U.removeAll(S);
        }
    }

    private static Set<Integer> selectMaxSet(Set<Integer> u) {
        long max = 0;
        Set<Integer> resSet = null;

        for(Set<Integer> s : F) {
            long count = s.stream()
                    .filter(u::contains)
                    .count();

            if (count > max) {
                max = count;
                resSet = s;
            }
        }

        System.out.println();
        System.out.println("Max Set:");
        printSet(resSet);
        System.out.println();
        return resSet;
    }

    private static void printSet(Set<Integer> s) {
        System.out.print("{");
        for (int i : s) {
            System.out.print(i + ",");
        }
        System.out.print("}");
    }

}
