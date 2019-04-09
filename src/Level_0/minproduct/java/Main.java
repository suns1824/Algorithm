package Level_0.minproduct.java;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int n;
    static int[] a;
    static int[] b;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        a = new int[n];
        b = new int[n];
        for(int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 0; i < n; i++) {
            b[i] = in.nextInt();
        }
        Arrays.sort(a);
        Arrays.sort(b);
        int sum = 0;
        for(int i = 0; i < n; i++) {
            sum += a[i]  * b[n - i - 1];
        }
        System.out.print(sum);
    }
}
