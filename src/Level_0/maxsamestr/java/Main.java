package Level_0.maxsamestr.java;

import java.util.Scanner;

public class Main {
    static String a;
    static String b;
    static int max;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        a = in.next();
        b = in.next();
        getCommonStr(a, b);

    }
    static void getCommonStr(String a, String b) {
        char[] aArray = a.toCharArray();
        char[] bArray = b.toCharArray();
        int aLen = aArray.length;
        int bLen = bArray.length;
        int maxI = 0;
        int maxJ = 0;
        int[][] dp = new int[aLen + 1][bLen + 1];
        for (int i = 1; i < aLen + 1; i++) {
            for(int j = 1; j < bLen + 1; j++) {
                if (aArray[i - 1] == bArray[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        for (int i = 0; i < aLen + 1; i++) {
            for (int j = 0; j < bLen + 1; j++) {
                if (dp[i][j] > max) {
                    max = dp[i][j];
                    maxI = i;
                    maxJ = j;
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < max; i++) {
            builder.append(aArray[maxI - max + i]);
        }
        System.out.println(builder.toString());
    }
}
