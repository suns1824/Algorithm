package Base.sort.insertsort;

public class Main {
    /*
    插入排序，保证在n趟排序后，0 - n-1上的元素已经为排序状态。
     */
    static int[] a = {133,454,201,183,126,900,18,666,5};
    public static void main(String[] args) {
        insertSort(a);
        for(int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    static void insertSort(int[] a) {
        for(int i = 1; i < a.length; i++) {
            int tmp = a[i];
            for(int j = i - 1; j >= 0; j--) {
                //从小到大排列
                if(a[j] > tmp) {
                    a[j + 1] = a[j];
                    if(j == 0) {
                        a[0] = tmp;
                    }
                } else {
                    a[j + 1] = tmp;
                    break;
                }
                ;
            }

        }
    }
}
