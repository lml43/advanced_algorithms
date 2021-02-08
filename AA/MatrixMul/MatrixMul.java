package MatrixMul;

import java.util.Arrays;
import java.util.List;

public class MatrixMul {

    static int[][] A = {
            {1,2,3,4},
            {2,3,4,5},
            {3,4,5,6},
            {4,5,6,7}
    };

    static int[][] B = {
            {6,9,4,3},
            {7,5,2,6},
            {2,2,6,1},
            {5,1,1,3}
    };


    public static void main(String[] args) {
//        List<int[][]> ints = divideMatrix(A);

        int[][] mul = mul(A, B, "AxB");
    }

    private static int[][] mul(int[][] A, int[][] B, String name) {
        int n = A.length;
        System.out.println();
        System.out.println("################## " + name + " (N=" + n + ") ################");
        System.out.println("Mat 1:");
        printMat(A);

        System.out.println();
        System.out.println("Mat 2:");
        printMat(B);
        System.out.println();

        int[][] C = new int[n][n];

        if (A.length == 2) {
            int m1 = (A[0][1] - A[1][1])*(B[1][0] + B[1][1]);
            System.out.println("M1=" + m1);
            int m2 = (A[0][0] + A[1][1])*(B[0][0] + B[1][1]);
            System.out.println("M2=" + m2);
            int m3 = (A[0][0] - A[1][0])*(B[0][0] + B[0][1]);
            System.out.println("M3=" + m3);
            int m4 = (A[0][0] + A[0][1])*B[1][1];
            System.out.println("M4=" + m4);
            int m5 = A[0][0]*(B[0][1] - B[1][1]);
            System.out.println("M5=" + m5);
            int m6 = A[1][1]*(B[1][0] - B[0][0]);
            System.out.println("M6=" + m6);
            int m7 = (A[1][0] + A[1][1])*B[0][0];
            System.out.println("M7=" + m7);

            C[0][0] = m1+m2-m4+m6;
            C[0][1] = m4+m5;
            C[1][0] = m6+m7;
            C[1][1] = m2-m3+m5-m7;
        } else {
            List<int[][]> subMatA = divideMatrix(A);
            int[][] A11 = subMatA.get(0);
            int[][] A12 = subMatA.get(1);
            int[][] A21 = subMatA.get(2);
            int[][] A22 = subMatA.get(3);

            List<int[][]> subMatB = divideMatrix(B);
            int[][] B11 = subMatB.get(0);
            int[][] B12 = subMatB.get(1);
            int[][] B21 = subMatB.get(2);
            int[][] B22 = subMatB.get(3);

            int[][] M1 = mul(minus(A12, A22),add(B21, B22), "M1");
            int[][] M2 = mul(add(A11, A22), add(B11, B22), "M2");
            int[][] M3 = mul(minus(A11, A21), add(B11, B12), "M3");
            int[][] M4 = mul(add(A11, A12), B22, "M4");
            int[][] M5 = mul(A11, minus(B12, B22), "M5");
            int[][] M6 = mul(A22, minus(B21, B11), "M6");
            int[][] M7 = mul(add(A21, A22), B11, "M7");

            int[][] C11 = add(minus(add(M1,M2),M4),M6);
            int[][] C12 = add(M4, M5);
            int[][] C21 = add(M6, M7);
            int[][] C22 = add(minus(M2,M3),minus(M5,M7));

            for (int i = 0; i < n/2; i++) {
                System.arraycopy(C11[i], 0, C[i], 0, n / 2);
                System.arraycopy(C12[i], 0, C[i], n/2, n / 2);
                System.arraycopy(C21[i], 0, C[i+n/2], 0, n / 2);
                System.arraycopy(C22[i], 0, C[i+n/2], n/2, n / 2);
            }
        }

        System.out.println();
        System.out.println(name + " (N=" + n + ")");
        printMat(C);

        return C;
    }

    private static int[][] add(int[][] matA, int[][] matB) {
        int n = matA.length;

        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = matA[i][j] + matB[i][j];
            }
        }

        return res;
    }

    private static int[][] minus(int[][] matA, int[][] matB) {
        int n = matA.length;

        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = matA[i][j] - matB[i][j];
            }
        }

        return res;
    }

    private static List<int[][]> divideMatrix(int[][] mat) {
        if (mat.length % 2 != 0 || mat[0].length % 2 != 0) {
            return null;
        }

        int newN = mat.length / 2;

        int[][] mat11 = new int[newN][newN];
        int[][] mat12 = new int[newN][newN];
        int[][] mat21 = new int[newN][newN];
        int[][] mat22 = new int[newN][newN];

        for (int i = 0; i < newN; i++) {
            System.arraycopy(mat[i], 0, mat11[i], 0, newN);
            System.arraycopy(mat[i], newN, mat12[i], 0, newN);
            System.arraycopy(mat[i+newN], 0, mat21[i], 0, newN);
            System.arraycopy(mat[i+newN], newN, mat22[i], 0, newN);
        }

        return Arrays.asList(mat11, mat12, mat21, mat22);
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
