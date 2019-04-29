package Cell;

import java.util.*;

public class C {

    private static  int LOOP_SIZE = 30;
    //增殖概率
    private static  float SPLIT_P = 0;
    //表征黏附强度
    private static  double DENPEND_P = 0.9;
    //填充度
    private static double FILL_P = 0.25;

    static boolean endFlag = false;
    static int max;
    static int UP_BOUNDARY = 10;
    static ArrayList<Node> nodeList = new ArrayList<>();
    static int len = 10;
    static HashSet<Node> allSet = new HashSet<>();
    public static void main(String[] args) {
        //初始化nodelist
        initState(FILL_P);
        initNodes(nodeList);

        for(int loop = 0; loop < LOOP_SIZE; loop++) {
            for(int i = 0; i < len * len; i++) {
                Node node = nodeList.get(i);
                if (node.value == 1) {
                    double transferP = getTransferP(node);
                    double random = Math.random();
                    //需要转移或者增殖,当前不考虑增殖
                    if (random < transferP) {
                        int pos = transferTo(node);
                        int curpos = nodeList.indexOf(node);
                        //交换两个node
                        Collections.swap(nodeList, pos, curpos);
                        //更新两个node的临近节点信息
                        node.setNearNodes(setNearNode(node));
                        nodeList.get(curpos).setNearNodes(setNearNode(nodeList.get(curpos)));
                    }
                }
            }
            for(int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    System.out.print(nodeList.get(i * len + j).value + " ");
                }
                System.out.println(" ");
            }
            for(int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    System.out.print(nodeList.get(i * len + j).pos + " ");
                }
                System.out.println(" ");
            }
            System.out.println("-----------------------------");
            Iterator itr = nodeList.iterator();
            while (itr.hasNext()) {
                Node node = (Node) itr.next();
                if (!allSet.contains(node) && node.value == 1) {
                    int tmp = findMaxGraphByDfs(node).size();
                    if (tmp > max) {
                        max = tmp;
                    }
                    if (max > UP_BOUNDARY) {
                        System.out.println(max);
                        endFlag = true;
                        break;
                    }
                }
            }
            allSet.clear();
            if (endFlag) {
                break;
            }
        }
    }


    private static int transferTo(Node node) {
        //处理四个边界的反弹逻辑
        //最上层
        int index = nodeList.indexOf(node);
        if (index / len == 0) {
            if (nodeList.get(index + len).value == 0) {
                return index + len;
            } else {
                return index;
            }
        }
        //最下层
        if (index / len == len - 1) {
            if (nodeList.get(index - len).value == 0) {
                return index - len;
            } else {
                return index;
            }
        }
        //最左边
        if (index % len == 0) {
            if (nodeList.get(index + 1).value == 0) {
                return index + 1;
            } else {
                return index;
            }
        }
        //最右边
        if (index % len == len - 1) {
            if (nodeList.get(index - 1).value == 0) {
                return index - 1;
            } else {
                return index;
            }
        }

        // 处理内部的节点，这些节点不需要考虑反弹逻辑
        ArrayList<Node> nearList = node.nearNodes;
        ArrayList<Integer> zeroList = new ArrayList<>();
        if(!nearList.contains(nodeList.get(index - len))) {
            zeroList.add(index - len);
        }
        if(!nearList.contains(nodeList.get(index + 1))) {
            zeroList.add(index + 1);
        }
        if(!nearList.contains(nodeList.get(index + len))) {
            zeroList.add(index + len);
        }if(!nearList.contains(nodeList.get(index - 1))) {
            zeroList.add(index - 1);
        }

        int nearNodeNum = nearList.size();
        //选择转移到周围为0的所有点的第几个上去
        int random = (int) Math.random() * (4 - nearNodeNum);

        return zeroList.get(random);
    }

    //转移概率 + 增殖概率
    private static double getTransferP(Node node) {
        int n = node.nearNodes.size();
        return n == 4 ? 0 : (1 - SPLIT_P) * Math.pow((1 - DENPEND_P), n) + SPLIT_P;
    }

    private static void initState(double prob) {
        for(int i = 0; i < len * len; i++) {
            int value = Math.random() > (1 - prob) ? 1 : 0;
            nodeList.add(new Node(value, i));
        }
        for(int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.print(nodeList.get(i * len + j).value + " ");
            }
            System.out.println(" ");
        }
        System.out.println("---------");
    }

    /*
    初始化设置node的临近节点信息
     */
    static void initNodes(List<Node > nodeList) {
        Iterator itr = nodeList.listIterator();
        while (itr.hasNext()) {
            Node node = (Node) itr.next();
            if (node.value == 0){
                continue;
            }
            node.setNearNodes(setNearNode(node));
        }
    }

    /*
    深度优先遍历
     */
    static ArrayList<Node> findMaxGraphByDfs(Node node) {
        ArrayList<Node> subGraphList = new ArrayList<>();
        if (node == null) {
            return null;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.push(node);
        set.add(node);
        allSet.add(node);
        subGraphList.add(node);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for(Node next: cur.getNearNodes()) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    allSet.add(next);
                    subGraphList.add(next);
                    break;
                }
            }
        }
        //System.out.println(subGraphList.size());
        return subGraphList;
    }

    static ArrayList<Node> setNearNode(Node node) {
//        if(node.value == 0){
//            throw new NullPointerException();
//        }
        ArrayList<Node> nearList = new ArrayList<>();
        int pos = nodeList.indexOf(node);
        if ((pos - len) >= 0 && (pos - len) < nodeList.size() && nodeList.get(pos - len).value == 1) {
            nearList.add(nodeList.get(pos - len));
        }
        if ((pos - 1) >= 0 && (pos - 1) < nodeList.size() && nodeList.get(pos - 1).value == 1) {
            nearList.add(nodeList.get(pos - 1));
        }
        if ((pos + 1) >= 0 && (pos + 1) < nodeList.size() && nodeList.get(pos + 1).value == 1) {
            nearList.add(nodeList.get(pos + 1));
        }
        if ((pos + len) >= 0 && (pos + len) < nodeList.size() && nodeList.get(pos + len).value == 1) {
            nearList.add(nodeList.get(pos + len));
        }
        return nearList;
    }

    private static class Node {
        int value;
        int pos;
        ArrayList<Node> nearNodes;

        public Node(int value, int pos) {
            this.value = value;
            this.pos = pos;
        }

        @Override
        public boolean equals(Object node) {
            return node.hashCode() == this.hashCode();
        }

        @Override
        public int hashCode() {
            return this.pos;
        }

        public List<Node> getNearNodes() {
            return nearNodes;
        }

        public void setNearNodes(ArrayList<Node> nearNodes) {
            this.nearNodes = nearNodes;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }
    }
}
