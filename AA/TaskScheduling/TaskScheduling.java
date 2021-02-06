package TaskScheduling;

import java.util.*;

public class TaskScheduling {

    static List<Task> tasks = Arrays.asList(
            new Task("z1", 4, 70),
            new Task("z2", 2, 60),
            new Task("z3", 4, 50),
            new Task("z4", 3, 40),
            new Task("z5", 1, 30),
            new Task("z6", 4, 20),
            new Task("z7", 6, 10)
    );

    public static void main(String[] args) {

        printList(tasks);
        List<Task> A = new ArrayList<>();
        List<Task> penList = new ArrayList<>();
        System.out.println();

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println();
            System.out.print((i+1) + ". (");

            A.add(tasks.get(i));

            if (!isIndependent(A)) {
                A.remove(A.size()-1);
                penList.add(tasks.get(i));
            }

            for (Task task : A) {
                System.out.print(task.name + ", ");
            }
            System.out.print(")");
        }

        int penalty = penList.stream().mapToInt(x -> x.penalty).sum();
        System.out.println();
        System.out.println();
        System.out.println("Penalty: " + penalty);
        penList.forEach(System.out::println);

        sortByDeadline(A);
        sortByDeadline(penList);

        A.addAll(penList);
        System.out.println();
        System.out.println("Optimal schedule: ");
        for (Task task : A) {
            System.out.print(task.name + ", ");
        }
    }

    private static void sortByDeadline(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(o -> o.deadline));
    }

    private static void printList(List<Task> lstTasks) {
        System.out.print("z");
        lstTasks.forEach(x -> System.out.print(String.format("%8s", x.name)));
        System.out.println();
        System.out.print("d");
        lstTasks.forEach(x -> System.out.print(String.format("%8s", x.deadline)));
        System.out.println();
        System.out.print("w");
        lstTasks.forEach(x -> System.out.print(String.format("%8s", x.penalty)));
    }

    private static boolean isIndependent(List<Task> tasks) {
        int t = tasks.stream()
                .mapToInt(task -> task.deadline)
                .filter(task -> task >= 0)
                .max()
                .orElse(0);

        t = Math.min(t, tasks.size());

        int finalT = t;
        long countTasks = tasks.stream()
                .mapToInt(task -> task.deadline)
                .filter(dl -> dl <= finalT)
                .count();
        return countTasks <= t;
    }

    private static class Task {

        public Task(String name, int deadline, int penalty) {
            this.name = name;
            this.deadline = deadline;
            this.penalty = penalty;
        }

        String name;
        int deadline;
        int penalty;


        @Override
        public String toString() {
            return "Task{" +
                    "name='" + name + '\'' +
                    ", deadline=" + deadline +
                    ", penalty=" + penalty +
                    '}';
        }
    }
}
