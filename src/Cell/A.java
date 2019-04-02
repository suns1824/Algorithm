package Cell;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Scanner;

//模拟细胞分裂，矩阵生成
public class A {
    private static int SIZE = 36;
    private static  int LOOP_SIZE = 3;
    //增殖概率
    private static  float SPLIT_P = 0;
    //表征黏附强度
    private static  double DENPEND_P = 0.9;
    //填充度
    private static double FILL_P = 0.2;
    /*
    模式类别：
    0： 随机生成初始矩阵
    1： 指定初始矩阵
     */
    private static int mode = 0;
    static int count = 0;

    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
        SIZE = in.nextInt();
        LOOP_SIZE = in.nextInt();
        SPLIT_P = in.nextFloat();
        DENPEND_P = in.nextDouble();
        FILL_P = in.nextDouble();
        File file = new File("result.txt");
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        int[][] initMatrix = initState(SIZE, FILL_P, mode);
        for(int m = 0; m < LOOP_SIZE; m++) {
            for(int i = 0; i < SIZE; i++) {
                for(int j = 0; j < SIZE; j++) {
                    //如果占位为1，则代表有细胞需要处理
                    if (initMatrix[i][j] == 1) {
                        //先通过转移+增殖概率决定是否转移或增殖
                        double transferP = getTransferP(i, j, initMatrix);
                        double tmpP = Math.random();
                        if (transferP > tmpP) {
                            int transPos = transferTo(i, j, initMatrix);
                            switch (transPos) {
                                case 0:
                                    initMatrix[i-1][j] = initMatrix[i][j];
                                    break;
                                case 1:
                                    initMatrix[i][j+1] = initMatrix[i][j];
                                    break;
                                case 2:
                                    initMatrix[i+1][j] = initMatrix[i][j];
                                    break;
                                case 3:
                                    initMatrix[i][j-1] = initMatrix[i][j];
                                    break;
                                case -1:
                                    break;
                            }

                            if (tmpP < transferP && tmpP > SPLIT_P) {
                                initMatrix[i][j] = 0;
                            }
                        }
                    }
                }
            }
//            for(int x = 0; x < SIZE; x++) {
//                for(int y = 0; y < SIZE; y++) {
//                    if (y == SIZE -1) {
//                        System.out.println(initMatrix[x][y]);
//                    } else {
//                        System.out.print(initMatrix[x][y] + ",");
//                    }
//                }
//            }
            System.out.println("-------------------------------------------------------------------------------");
            //写入到本地文件中去
            writer.write("round: " + ++count + "\r\n");
//            System.out.println("矩阵： \r\n");
//            for(int x = 0; x < SIZE; x++) {
//                for(int y = 0; y < SIZE; y++) {
//                    if (y == SIZE -1) {
//                        writer.write(initMatrix[x][y] + "\r\n");
//                    } else {
//                        writer.write(initMatrix[x][y] + ",");
//                    }
//                }
//            }
//            writer.write("\r\n");

        }
        writer.flush();
        writer.close();
    }

    private static int[][] initState(int size, double prob, int mode) {

        if (mode == 1) {
            //处理
            int[][] initMatrix = {{1, 2, 0}, {1, 2, 5},{0,0,0}};
            return initMatrix;
        }
        int[][] initMatrix = new int[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                initMatrix[i][j] = Math.random() > (1 - prob) ? 1 : 0;
//                initMatrix[i][j] = (i%2 == 0 && j%2 == 1) ? 1 : 0;
                if (j == size - 1) {
                    System.out.println(initMatrix[i][j]);
                } else {
                    System.out.print(initMatrix[i][j] + ",");
                }
            }
        }
        System.out.println("-------------------------------------------------------------------------------");
        return initMatrix;
    }

    private static Node getNode(int i, int j, int[][] matrix) {
        Node node = new Node();
        int n = 0;
        int leftStatus = j==0 ? -1 : matrix[i][j-1];
        int rightStatus = j == SIZE -1 ? -1 : matrix[i][j + 1];
        int upStatus = i==0 ? -1 : matrix[i-1][j];
        int downStatus = i== SIZE -1 ? -1 : matrix[i+1][j];
        n = (leftStatus == -1 ? 1 : leftStatus) + (rightStatus == -1 ? 1 : rightStatus) + (upStatus == -1 ? 1 : upStatus) + (downStatus == -1 ? 1 : downStatus);
        int[] status = {upStatus, rightStatus, downStatus, leftStatus};
        ArrayList<Integer> emptyArray = new ArrayList<>();
        for(int m = 0; m < 4; m++) {
            if (status[m] == 0) {
                emptyArray.add(m);
            }
        }
        node.setN(n);
        //记录周围空节点的位置
        node.setEmptyList(emptyArray);
        return node;
    }
    //转移概率+增值概率
    private static double getTransferP(int i, int j, int[][] matrix) {
        int n = getNode(i, j, matrix).getN();
        return n == 4 ? 0 : (1 - SPLIT_P) * Math.pow((1 - DENPEND_P), n) + SPLIT_P;
    }

    private boolean isClustered(int num) {
        return false;
    }

    private static int transferTo(int i, int j, int[][] matrix) {
        //加入反弹逻辑
        if (i == 0) {
            if (matrix[i + 1][j] == 0) {
                return 2;
            }
        } else if (i == SIZE -1) {
            if (matrix[i - 1][j] == 0) {
                return 0;
            }
        }else if (j == 0) {
            if (matrix[i][j + 1] == 0) {
                return 1;
            }
        } else if(j == SIZE -1) {
            if (matrix[i][j - 1] == 0) {
                return 3;
            }
        }

        Node node = getNode(i, j, matrix);
        int n = node.getN();
        ArrayList<Integer> emptyList = node.getEmptyList();
        if (n == 4) {
            return -1;
        }
        int random = (int)(Math.random() * (4 - n));
        if (n == 0) {
            return random;
        }
        if (n == 1 || n == 2) {
            return emptyList.get(random);
        }
        if (n == 3) {
            return emptyList.get(0);
        }
        return -1;
    }

    static class Node {
        private int id;
        private int n;
        private ArrayList<Integer> emptyList;
        private int splitCount;

        public int getId() {
            return id;
        }
        public void setId() {
            this.id = id;
        }

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        public ArrayList<Integer> getEmptyList() {
            return emptyList;
        }

        public void setEmptyList(ArrayList<Integer> emptyList) {
            this.emptyList = emptyList;
        }

        public int getSplitCount() {
            return splitCount;
        }

        public void setSplitCount(int splitCount) {
            this.splitCount = splitCount;
        }
    }

    static class Pos {
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
