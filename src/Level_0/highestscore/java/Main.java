package Level_0.highestscore.java;

import java.util.Scanner;

public class Main {
    static int n;
    static int m;
    static int[] a; // 分数
    static int[] b; // 时间
    static int[] score;
    //这道题和plusequalm类似，不能重复，所以需要一个二维数组记录一下是否已经做过该题
    static boolean[][] record;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        a = new int[n];
        b = new int[n];
        for(int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        for(int i = 0; i < n; i++) {
            b[i] = in.nextInt();
        }
        m = in.nextInt();
        score = new int[m + 1];
        record = new boolean[m + 1][n];
        f(m);
        for(int i = 0; i < m + 1; i++){
            System.out.println(i + ":" + score[i]);
        }
    }

    /*
    2
    4 2
    3 1
    4
    f(n) = max{f(n - b[i]) + a[i]}
     */
    static void f(int m) {
        for(int i = 0; i < m + 1; i++) {
            if (i == 0) {
                score[0] = 0;
                continue;
            }
            int max = 0;
            int preIndex = 0;
            int addedIndex = -1;
            for(int j = 0; j < n; j++) {
                if ((i - b[j]) >= 0 && !whetherUsed(i - b[j], j)) {
                    int tmp = max;
                    max = max(score[i - b[j]] + a[j], max);
                    if (max > tmp) {
                        preIndex = i - b[j];
                        addedIndex = j;
                    }
                }
            }
            score[i] = max;
            //更新record[i]
            for(int j = 0; j < n; j++) {
                record[i][j] = record[preIndex][j];
            }
            if (addedIndex != -1) {
                record[i][addedIndex] = true;
            }
        }
    }

    static boolean whetherUsed(int i, int j) {
        if (record[i][j]) {
            return true;
        }
        return false;
    }

    static int max(int a, int b) {
        return a > b ? a: b;
    }
}
