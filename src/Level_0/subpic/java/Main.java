package Level_0.subpic.java;

import java.util.*;

public class Main {
    static int max;
    static ArrayList<Node> nodeList = new ArrayList<>();
    static int len = 0;
    static HashSet<Node> allSet = new HashSet<>();
    public static void main(String[] args) {
        //读取test.txt中的矩阵
        readFile();
        initNodes(nodeList);
        Iterator itr = nodeList.iterator();
        while (itr.hasNext()) {
            Node node = (Node) itr.next();
            if (!allSet.contains(node) && node.value == 1) {
                int tmp = findMaxGraphByDfs(node);
                if (tmp > max) {
                    max = tmp;
                }
            }
        }
        System.out.println(max);
    }
    static void readFile() {
        ArrayList<String> matrixList= (ArrayList<String>) ReadFile.readFileByLines("src/Level_0/subpic/java/test.txt");
        len = matrixList.size();
        int index = 0;
        for(int i = 0; i < len; i++) {
            String tmp = matrixList.get(i);
            String[] tmpStr = tmp.split(" ");
            for(int j = 0; j < len; j++) {
                if (index < len * len) {
                    nodeList.add(new Node(Integer.parseInt((tmpStr[j])), index));
                    index++;
                    //System.out.print(tmpStr[j]);
                }
            }
            //System.out.println(" ");
        }
    }

    static void initNodes(ArrayList<Node> nodeList) {
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
    static int findMaxGraphByDfs(Node node) {
        ArrayList<Node> subGraphList = new ArrayList<>();
        if (node == null) {
            return -1;
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
        return subGraphList.size();
    }

    /**
     *广度优先遍历的方式寻找，待完善
     */

    static ArrayList<Node> setNearNode(Node node) {
        if(node.value == 0){
            return null;
        }
        ArrayList<Node> nearList = new ArrayList<>();
        int pos = node.getPos();
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
