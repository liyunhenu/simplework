package com.imooc.linkedlist;

public class ReverseKLinkedList {

    public static void main(String[] args) {
        Node node = new Node("0");
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        Node node4 = new Node("4");
        Node node5 = new Node("5");
        Node node6 = new Node("6");
        node.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);
        node5.setNext(node6);
        Node head = node;
        while (head != null) {
            System.out.print(head.getValue() + "->");
            head = head.next;
        }
        System.out.println();
        System.out.println("反转结果：");
        Node newHead = reverseK(node, 3);
        head = newHead;
        while (head != null) {
            System.out.print(head.getValue() + "->");
            head = head.next;
        }
    }


    public static Node reverse(Node node) {
        while (node == null || node.next == null) {
            return node;
        }
        Node newHead = reverse(node.next);
        node.next.next = node;
        node.next = null;
        return newHead;
    }


    public static Node reverseK(Node node, int k) {
        int i = k;
        Node oldHead = node;
        Node newHead = null;
        while (i > 0 && node != null) {
            node = node.next;
            i--;
            if (i == 1) {
                newHead = node;
            }
        }
        if (i > 0) {//不够k个元素，提前结束
            return oldHead;
        } else {
            Node tmp = reverseK(node, k);
            newHead.next = null;//断掉K组最后一个节点指向后一个节点连接
            Node tail = oldHead;//K反转后,头变成k的尾结点
            reverse(oldHead);
            tail.next = tmp;
            return newHead;
        }

    }
}
