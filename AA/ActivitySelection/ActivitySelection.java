import java.util.*;

public class ActivitySelection {

    static List<Task> tasks = Arrays.asList(
            new Task(0, 4),
            new Task(2, 6),
            new Task(4, 7),
            new Task(7, 10),
            new Task(1, 3),
            new Task(7, 9)
    );

    public static void main(String[] args) {
        sort();
        greedy();
    }

    private static void sort() {
        tasks.sort(Comparator.comparingInt(o -> o.finish));
        System.out.println("Sort tasks: ");
        System.out.print("i");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.print(String.format("%8s", i+1));
        }
        printList(tasks);
    }

    private static void greedy() {
        System.out.println();
        System.out.println();
        System.out.println("Greedy: ");
        System.out.print("i");

        int n = tasks.size();
        List<Task> resList = new ArrayList<>(Collections.singletonList(tasks.get(0)));
        System.out.print(String.format("%8s", 1));

        int j = 0;
        for (int i = 1; i < n; i++) {
            if (tasks.get(i).getStart() >= tasks.get(j).getFinish()) {
                resList.add(tasks.get(i));
                j = i;
                System.out.print(String.format("%8s", i+1));
            }
        }


        printList(resList);

    }

    private static void printList(List<Task> lstTasks) {

        System.out.println();
        System.out.print("s");
        lstTasks.forEach(x -> System.out.print(String.format("%8s", x.start)));
        System.out.println();
        System.out.print("f");
        lstTasks.forEach(x -> System.out.print(String.format("%8s", x.finish)));
    }

    private static class Task {
        int start;
        int finish;

        public Task(int start, int finish) {
            this.start = start;
            this.finish = finish;
        }

        public int getStart() {
            return start;
        }

        public int getFinish() {
            return finish;
        }

        @Override
        public String toString() {
            return "{"+ start +
                    ", " + finish +
                    '}';
        }
    }
}
