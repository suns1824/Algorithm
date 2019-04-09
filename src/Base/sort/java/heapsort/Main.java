package Base.sort.java.heapsort;

public class Main {
    /*
          10
    25           5
 15   20       30
 建立大根堆
     */
    static int[] a = {10, 25, 5, 15, 20, 30};
    static int len = a.length;  //6
    public static void main(String[] args) {
        heapSort(a);
        for(int i = 0; i < len; i++) {
            System.out.print(a[i] + " ");
        }
    }

    static int leftChild(int i ) {
        return 2 * i + 1;
    }

    static void percolateDown(int i, int len) {
        int child;
        int tmp;
        for(tmp = a[i]; leftChild(i) < len; i = child) {
            child = leftChild(i);
            if (child != len - 1 && a[child] < a[child + 1]) {
                child ++;
            }
            if (tmp < a[child]) {
                a[i] = a[child];
            } else {
                break;
            }
        }
        a[i] = tmp;
    }

    static void heapSort(int[] a) {
        //建堆
        for(int i = a.length / 2 - 1; i >= 0; i--) {
            percolateDown(i, len);
        }

        //删除max
        for(int i = a.length - 1; i > 0; i--) {
            swap(0, i);
            percolateDown(0, i);
        }
    }

    static void swap(int i , int j) {
        int tmp;
        tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
