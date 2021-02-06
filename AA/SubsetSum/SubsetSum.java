package SubsetSum;

import java.util.ArrayList;

public class SubsetSum {

    public static void main(String[] args) {
        ArrayList<Integer> set = new ArrayList<Integer>();
        ArrayList<Integer> l = new ArrayList<Integer>();

        double epsilon = 0.4d;  // Input
        int t = 3600;   // Input

        set.add(765);   // Input
        set.add(855);  // Input
        set.add(1710);  // Input
        set.add(1746);  // Input
        set.add(1800);  // Input

        double delta = epsilon / (2 * set.size());

        l.add(0);

        for (int i = 0; i < set.size(); i++) {
            int size = l.size();
            for (int j = 0; j < size; j++) {
                l.add(l.get(j) + set.get(i));
            }
            l.sort((o1, o2) -> o1 - o2);
            System.out.println("Step " + (i + 1) + " (" + set.get(i) + ") :" + "\n");
            System.out.print("Add: ");
            PrintSet(l);
            System.out.print("\nRemove: ");
            RemoveElements(l,t);
            PrintSet(l);
            System.out.print("\nTrim: ");
            TrimSet(l, delta);
            PrintSet(l);
            System.out.println("\n");
        }


    }

    public static void TrimSet(ArrayList<Integer> l, double delta) {
        for (int i = 0; i < l.size() - 1; i++) {
            if ((double)(l.get(i) * (1 + delta)) >+ (double)(l.get(i + 1))) {
                l.remove(i + 1);
                i--;
            }
        }
    }

    public static void RemoveElements(ArrayList<Integer> l, int t) {
        for (int i = 0; i < l.size(); i++) {
            if(l.get(i) > t){
                l.remove(i);
                i--;
            }
        }
    }


    public static void PrintSet(ArrayList<Integer> l) {
        for (int e : l) {
            System.out.print(e + ", ");
        }
    }
}
