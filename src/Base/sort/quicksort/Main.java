package Base.sort.quicksort;

import java.util.ArrayList;
import java.util.List;

public class Main {
    /*
    快速排序
     */
    static int[] a = {133,454,201,183,126,900,18,666,5};
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < a.length; i++) {
            list.add(a[i]);
        }
        //递归
        //sortA(list);

        //非递归


        for (Integer i : list) {
            System.out.print(i + " ");
        }
    }

    /*
    非递归
     */


    /*
    递归
     */
    static void sortA(List<Integer> a) {
        if(a.size() > 0) {
            List<Integer> smaller = new ArrayList<>();
            List<Integer> same = new ArrayList<>();
            List<Integer> bigger = new ArrayList<>();
            int midIndex = a.size() / 2;
            int mid = a.get(midIndex);
            for (Integer i: a) {
                if (i < mid) {
                    smaller.add(i);
                } else if (i > mid) {
                    bigger.add(i);
                } else {
                    same.add(i);
                }
            }
            sortA(smaller);
            sortA(bigger);
            a.clear();
            a.addAll(smaller);
            a.addAll(same);
            a.addAll(bigger);
        }
    }
}
