package MatrixChain;

public class MatrixChain {

    static int[] p = {5,4,6,2,7};
    static int[][] m;
    static int[][] s;

    public static void main(String[] args) {

        int n = p.length - 1;
        m = new int[n][n];
        s = new int[n][n];

        for (int i = 0; i <= 0; i++) {
            m[i][i] = 0;
        }

        for (int l = 2; l <= n; l++) {
            System.out.println();
            System.out.println("l = " + l);
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i+l-1;
                m[i-1][j-1] = Integer.MAX_VALUE;
                System.out.println("m(" + i + "," + j +")=");
                for (int k = i; k < j; k++) {
                    System.out.print("\t" + String.format("m(%d,%d) + m(%d,%d) + %d.%d.%d = ", i,k,k+1,j,p[i-1],p[k],p[j]));
                    int q = m[i-1][k-1] + m[k][j-1] + p[i-1]*p[k]*p[j];
                    System.out.println(q);
                    if (q < m[i-1][j-1]) {
                        m[i-1][j-1] = q;
                        s[i-1][j-1] = k;
                    }
                }
                System.out.println("=> m(" + i + "," + j +")=" + m[i-1][j-1]);
                System.out.println();

            }
        }

        System.out.println("m:");
        printMat(m);

        System.out.println();
        System.out.println("s:");
        printMat(s);
    }

    private static void printMat(int[][] mat) {

        System.out.print(String.format("%6s", "|"));
        for (int i = 0; i < mat[0].length; i++) {
            System.out.print(String.format("%6s", (i+1)));
        }
        System.out.println();
        for (int i = 0; i <= mat[0].length; i++) {
            System.out.print("______");
        }
        for (int i = 0; i < mat.length; i++) {
            System.out.println();
            System.out.print(String.format("%6s", (i+1)+" |"));
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(String.format("%6s", mat[i][j]));
            }
        }
        System.out.println();
    }

}
