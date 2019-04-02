package Level_0.coin.java;

import java.util.Scanner;

public class Main {
    /*
     n: 多少个面值
     a: 面值数组
     m: 需要凑的金额
      */
    private static int[] a;
    private static int[] b;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int count = n;
        a = new int[n];
        while (count-- > 0) {
            a[n - count -1] = in.nextInt();
        }
        int m = in.nextInt();
        //存放m元钱需要的最少硬币数
        b = new int[m + 1];
        for(int i = 0; i < m; i++) {
            b[i] = m + 1;
        }
        f(m);
        for(int i = 0; i < m + 1; i++) {
            System.out.println(i + " : " + b[i]);
        }
    }

    static int min(int a, int b) {
        return a < b ? a: b;
    }
    /*
    f(m) = min(f(m - a[i])) + 1
    m: 18:
    a[]: {1,3,6,7}
     */
    static void f(int m){
        for(int j = 0; j < m + 1; j++){
            if (j == 0) {
                b[0] = 0;
                continue;
            }
            int MIN = m + 1;
            for(int i = 0; i < a.length; i++) {
                if(j - a[i] >= 0){
                    MIN =  min(MIN, b[j - a[i]] + 1);
                }
                else break;
                b[j] = MIN;
            }
        }
    }
}
