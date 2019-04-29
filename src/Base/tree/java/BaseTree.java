package Base.tree.java;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BaseTree {

    /*
    遍历-递归,先序遍历， 中序后序同理
     */
    void preVisitRec(Node node) {
        visit(node);
        if (node.leftChild != null) {
            preVisitRec(node.leftChild);
        }
        if (node.rightChild != null) {
            preVisitRec(node.rightChild);
        }
    }

    /*
    层序遍历
     */
    void levelVisit(Node node) {
        if (node == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while ( !queue.isEmpty()) {
            Node tmp = queue.poll();
            visit(tmp);
            if (node.leftChild != null) {
                queue.add(node.leftChild);
            }
            if (node.rightChild != null) {
                queue.add(node.rightChild);
            }
        }

    }

    /*
    非递归先序
     */
    void preVisit(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                visit(node);
                stack.add(node);
                node = node.leftChild;
            }else {
                node = stack.pop();
                node = node.rightChild;
            }
        }
    }

    /*
    非递归中序遍历,和先序差不多
     */
    void  midVisit(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.add(node);
                node = node.leftChild;
            }else {
                node = stack.pop();
                visit(node);
                node = node.rightChild;
            }
        }
    }

    /*
    非递归后序遍历，略复杂
     */
    void postVisit(Node node) {
        Stack<Node> stack = new Stack<>();
        Node last = null;
        while (node != null && !stack.isEmpty()) {
            if (node != null) {
                stack.add(node);
                node = node.leftChild;
            } else {
                node = stack.peek();
                if (node.rightChild != null && node.rightChild != last) {
                    node = node.rightChild;
                    stack.add(node);
                    node = node.leftChild;
                } else {
                    node =stack.pop();
                    visit(node);
                    last = node;
                    node = null; //强制去探索右孩子节点
                }
            }
        }
    }

    /*
    计算树的高度
     */
    int getHeight(Node node) {
        if (node.leftChild == null && node.rightChild == null) {
            return 1;
        }
        int leftHeight = getHeight(node.leftChild);
        int rightHeight = getHeight(node.rightChild);
        int height = leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
        return height;
    }

    void visit(Node node) {
        System.out.print(node.element.toString() + " ");
    }

    static class Node<T> {
        T element;
        Node<T> leftChild;
        Node<T> rightChild;
    }
}
