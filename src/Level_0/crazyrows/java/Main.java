package Level_0.crazyrows.java;

import java.util.Scanner;

/*
确定每行最后一个1的位置，第一行应该是00...0或者100...00的形式。可以交换到第一行的行肯定可以交换到第2或者后面的行。
 */
public class Main {
    static int n;
    static int[][] matrix;
    static int[] lastArray;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        matrix = new int[n][n];
        lastArray = new int[n];
        for(int i = 0; i < n; i++) {
            lastArray[i] = -1;
            for(int j = 0; j < n; j++) {
                matrix[i][j] = in.nextInt();
                if(matrix[i][j] == 1) {
                    lastArray[i] = j;
                }
            }
        }

        int count = 0;
        for(int i = 0; i < n; i++) {
            int pos = -1;
           for(int j = i; j < n; j++) {
               if(lastArray[j] <= i) {
                   pos = j;
                   break;
               }
           }
           //逐行交换i和pos行
            for(int j = pos; j > i; j--) {
               //交换j和j-1行
                swap(j,j - 1);
                count++;
            }
        }
        System.out.print(count);
    }

    static void swap(int x, int y) {
        int tmp;
        tmp = lastArray[x];
        lastArray[x] = lastArray[y];
        lastArray[y] = tmp;
    }
}