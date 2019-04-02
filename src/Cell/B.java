package Cell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

//模拟细胞分裂，矩阵生成
public class B {

    private static List<Node> nodeList = new ArrayList<>();
    private static int len = 0;

    private static int SIZE = 36;
    private static  int LOOP_SIZE = 3;
    //增殖概率
    private static  float SPLIT_P = 0;
    //表征黏附强度
    private static  double DENPEND_P = 0.9;
    //填充度
    private static double FILL_P = 0.2;

    static int count = 0;

    static int[][] initMatrix = new int[SIZE][SIZE];

    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
        initState(SIZE, FILL_P);
        for(int itr = 0; itr < LOOP_SIZE; itr++) {
            Iterator iterator = nodeList.listIterator();
            while (iterator.hasNext()) {
                Node node = (Node) iterator.next();
                int i = node.getX();
                int j = node.getY();
                double transferP = getTransferP(i, j, initMatrix);
                double tmpP = Math.random();
                if (transferP > tmpP) {
                    int transPos = transferTo(i, j, initMatrix);
                    switch (transPos) {
                        case 0:
                            initMatrix[i-1][j] = initMatrix[i][j];
                            node.setX(i-1);
                            break;
                        case 1:
                            initMatrix[i][j+1] = initMatrix[i][j];
                            node.setY(j+1);
                            break;
                        case 2:
                            initMatrix[i+1][j] = initMatrix[i][j];
                            node.setX(i+1);
                            break;
                        case 3:
                            initMatrix[i][j-1] = initMatrix[i][j];
                            node.setY(j-1);
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

    private static void initState(int size, double prob) {

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                initMatrix[i][j] = Math.random() > (1 - prob) ? 1 : 0;
                if (initMatrix[i][j] == 1) {
                    nodeList.add(new Node(len++, i, j));
                }
            }
        }
    }

    //转移概率+增值概率
    private static double getTransferP(int i, int j, int[][] matrix) {
        int n = getN(i, j, matrix);
        return n == 4 ? 0 : (1 - SPLIT_P) * Math.pow((1 - DENPEND_P), n) + SPLIT_P;
    }

    private static int getN(int i, int j, int[][] matrix) {
        int n;
        int leftStatus = j==0 ? -1 : matrix[i][j-1];
        int rightStatus = j == SIZE -1 ? -1 : matrix[i][j + 1];
        int upStatus = i==0 ? -1 : matrix[i-1][j];
        int downStatus = i== SIZE -1 ? -1 : matrix[i+1][j];
        n = (leftStatus == -1 ? 1 : leftStatus) + (rightStatus == -1 ? 1 : rightStatus) + (upStatus == -1 ? 1 : upStatus) + (downStatus == -1 ? 1 : downStatus);

        return n;
    }

    private static ArrayList<Integer> getEmptyList(int i, int j, int[][] matrix) {
        int leftStatus = j==0 ? -1 : matrix[i][j-1];
        int rightStatus = j == SIZE -1 ? -1 : matrix[i][j + 1];
        int upStatus = i==0 ? -1 : matrix[i-1][j];
        int downStatus = i== SIZE -1 ? -1 : matrix[i+1][j];
        int[] status = {upStatus, rightStatus, downStatus, leftStatus};
        ArrayList<Integer> emptyArray = new ArrayList<>();
        for(int m = 0; m < 4; m++) {
            if (status[m] == 0) {
                emptyArray.add(m);
            }
        }
        return emptyArray;
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

        int n = getN(i, j, matrix);
        ArrayList<Integer> emptyList = getEmptyList(i, j, matrix);
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

    //判断范围n内的聚集状态
    private static boolean isCluster(Node node, int r, float boundary) {
        int sum = 0;
        int x = node.getX();
        int y = node.getY();
        int startX = (x - r) >= 0 ? x-r : 0;
        int endX = (x + r) <= SIZE ? x+r : SIZE;
        int startY = (y - r) >= 0 ? y-r : 0;
        int endY = (y + r) <= SIZE ? y + r : SIZE;
        for(int i = startX; i <= endX; i++){
            for (int j  = startY; j <= endY; j++) {
                if(initMatrix[i][j] == 1) {
                    sum++;
                }
            }
        }
        return sum/((endX-startX)*(endY-startY)) > boundary;
    }

    static class Node {
        private int id;
        private int x;
        private int y;


        public Node(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

