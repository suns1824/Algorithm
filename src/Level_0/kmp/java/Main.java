package Level_0.kmp.java;

import java.util.Scanner;

public class Main {
    /*
    a是主串，b是模式串,kmp的核心思想在于匹配失败时，b的指针j应该移动到哪里。
    b[0 - (k-1)] == b[j-k - j-1]
     */
    static String a;
    static String b;
    public static void main(String[] args) {
        Scanner in =  new Scanner(System.in);
        a = in.next();
        b = in.next();
        char[] aArray = a.toCharArray();
        char[] bArray = b.toCharArray();
        int i = 0;
        int j = 0;
        int[] next = getNext(b);
        while (i < aArray.length && j < bArray.length) {
            if (j == -1 || aArray[i] == bArray[j]) {
                i ++;
                j ++;
            } else {
                j = next[j];
            }
        }
        if (j == bArray.length) {
            System.out.println(i-j);
        } else {
            return;
        }
    }

    /*
    next[j]的值表示当b[j] != a[j]时，j指针要移动到的位置。
     */
    static int[] getNext(String b) {
        char[] bArray = b.toCharArray();
        int[] next = new int[bArray.length];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < bArray.length - 1) {
            if (k == -1 || bArray[j] == bArray[k]) {
                if(bArray[++j] == bArray[++k]) {
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }
}
