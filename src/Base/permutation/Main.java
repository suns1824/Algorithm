package Base.permutation;


/*
输出一个字符数组的所有排列组合，递归
 */
public class Main {
    static char[] array;
    public static void main(String[] args) {
        array = new char[]{'a', 'b', 'c', 'd'};
        char[][] result = permutae(0);
        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < array.length; j++) {
                System.out.print(result[i][j]);
            }
            System.out.println(" ");
        }
        System.out.println(result.length);
    }

    static char[][] permutae(int start) {
        char[][] result;
        if (start < array.length - 1) {
            char[][] tmp = permutae(start + 1);
            int len = tmp.length;
            result = new char[len * (array.length - start)][];
            int index = 0;
            for(int i = 0; i < len; i++) {
                for(int j = 0; j < (array.length - start); j++) {
                    result[index ++] = insert(start, tmp[i], j);
                }

            }
        } else {
            char[] tmp = new char[1];
            tmp[0] = array[array.length - 1];
            result = new char[1][1];
            result[0] = tmp;
        }
        return result;
    }

    static char[] insert(int start, char[] tmpArray, int index) {
        char[] result = new char[tmpArray.length + 1];
        int i;
        for(i = 0; i < index; i++) {
            result[i] = tmpArray[i];
        }
        result[i++] = array[start];
        for(i = index + 1; i < tmpArray.length + 1; i++) {
            result[i] = tmpArray[i - 1];
        }
        return result;
    }

}
