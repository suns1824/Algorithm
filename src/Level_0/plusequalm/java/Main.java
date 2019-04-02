package Level_0.plusequalm.java;

import java.util.Scanner;

public class Main {
    /*
    a： {1 3 5 6 7}
    m： 19
    f： 1-m每个数的true/false状态
    b:  二维数组记录a的使用情况，因为不能重复使用一个数
     */
    private static boolean[] f;
    private static int[][] b;
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        int n = in.nextInt();
        int count = n;
        int[] a = new int[n];
        while (count-- > 0) {
            a[n-count-1] = in.nextInt();
        }
        int m  = in.nextInt();
        f = new boolean[m+1];
        b = new int[m+1][n];
        for(int i = 0; i < n; i++) {
            f[i] = false;
        }
        for(int i = 0; i < m + 1; i++) {
            for(int j = 0; j < n; j++) {
                b[i][j] = Integer.MAX_VALUE;
            }
        }
        int min = min(a);

        //思路：f[i] = f[i - a[...]] = true && a[...]不在b[i - a[...]]的之中
        for(int i = 0; i < m + 1; i++) {
            if (i < min) {
                f[i] = false;
                continue;
            }
            if (i == min) {
                f[i] = true;
                b[i][0] = min;
                continue;
            }
            for(int j = 0; j < a.length; j++) {
                if(i - a[j] >= 0) {
                    if(i == a[j]) {
                        f[i] = true;
                        b[i][0] = a[j];
                        break;
                    }
                    boolean tmp = (f[i - a[j]]);
                    if(tmp) {
                        int tmpcount = 0;
                        for(int k = 0; k < n; k++) {
                            if (b[i - a[j]][k] != a[j]) {
                               tmpcount ++;
                            }
                        }
                        if (tmpcount == n) {
                            f[i] = true;
                            //更新b[i],加上a[j]
                            int tmpIndex = m;
                            for(int k = 0; k < n; k++) {
                              if(b[i - a[j]][k] != Integer.MAX_VALUE) {
                                  b[i][k] = b[i - a[j]][k];
                              }  else {
                                  tmpIndex = k;
                                  break;
                              }
                            }
                            b[i][tmpIndex] = a[j];
                            break;
                        }
                    }
                }
            }
        }
        for(int i = 0; i < m + 1; i++){
            System.out.println(i + ": " + f[i]);
        }

    }

    static int min(int[] a) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < a.length; i++) {
            if(a[i] < min) {
                min = a[i];
            }
        }
        return min;
    }

}
