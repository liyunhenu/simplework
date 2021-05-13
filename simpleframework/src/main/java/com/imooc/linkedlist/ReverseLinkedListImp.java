package com.imooc.linkedlist;

import java.util.LinkedList;

public class ReverseLinkedListImp {


    public static void main(String[] args) {
        Node node = new Node("0");
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        Node node4 = new Node("4");
        Node node5 = new Node("5");
        node.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);
        Node head = node;
        while (head != null) {
            System.out.print(head.getValue() + "->");
            head = head.next;
        }
        System.out.println();
        System.out.println("反转结果：");
        Node newHead = reverse1(node);
        head = newHead;
        while (head != null) {
            System.out.print(head.getValue() + "->");
            head = head.next;
        }


    }

    //递归
    private static Node reverse(Node node) {
        if (node == null || node.next == null) return node;
        Node head = reverse(node.next);
        node.next.next = node;//原节点的next还指向原来的node
        node.next = null;
        return head;
    }

    //遍历交换
    private static Node reverse1(Node node) {
        if (node == null || node.next == null) return node;

        Node pre = null;
        Node curr = node;
        Node nex = node.next;
        while (curr != null) {
            nex=curr.next;
            curr.next = pre;//断开原有连接，反指
            pre = curr;
            curr = nex;

        }
        return pre;
    }


}
