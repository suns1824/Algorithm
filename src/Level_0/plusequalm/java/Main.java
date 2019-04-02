package Level_0.plusequalm.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        int n = in.nextInt();
        int count = n;
        int[] a = new int[n];
        while (count-- > 0) {
            a[n-count-1] = in.nextInt();
        }
        int m  = in.nextInt();
    }
}
