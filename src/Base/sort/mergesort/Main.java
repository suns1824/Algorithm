package Base.sort.mergesort;

/*
归并排序
 */

public class Main {
    static int[] a = {10, 5, 30, 15, 20};
    public static void main(String[] args) {
        int len = a.length;
        int[] tmp = new int[len];
        mergeSort(tmp, 0, a.length - 1);
        for(int i = 0; i < len; i++) {
            System.out.print(a[i] + " ");
        }
    }


    static void mergeSort(int[] tmp, int left, int right) {
        if (left < right) {
            int midIndex = (left + right) / 2;
            mergeSort(tmp, left, midIndex);
            mergeSort(tmp, midIndex + 1, right);
            merge(tmp, left, midIndex + 1, right);
        } else {
            tmp[left] = a[left];
        }
    }

    /*
   10, 5, 30, 15, 20
   midIndex = (0+4)/2=2
   mergeSort(tmp,0,2)
   mergeSort(tmp,3,4)
   merge(tmp 0,3,4)

   mergeSort(tmp, 0,2) ==>
   mergeSort(tmp, 0,1) + mergeSort(tmp, 2,2) + merge(tmp, 0,2,2)

   mergeSort(tmp, 0,0) + mergeSort(tmp,1,1) + merge(tmp,0,1,1)


   mergeSort(tmp,3,4) ==>
   mergeSort(tmp,3,3)  +  mergeSort(tmp,4,4)  + merge(tmp, 3, 4, 4)
     */
    static void merge(int[] tmp, int left, int mid , int right) {
        int k = left;
        int i = left;
        int j = mid;
        while (i < mid && j <= right) {
            if (a[i] < a[j]) {
                tmp[k++] = a[i];
                i++;
            } else {
                tmp[k++]  = a[j];
                j++;
            }
        }
        if (j > right) {
            while (i < mid) {
                tmp[k++] = a[i++];
            }
        } else if (i >= mid) {
            while (j < right) {
                tmp[k++] = a[j++];
            }
        }
        for(int x = left; x <= right; x++) {
            a[x] = tmp[x];
        }
    }

}
