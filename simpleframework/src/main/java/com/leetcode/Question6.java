package com.leetcode;

/**
 * 反转链表
 */
public class Question6 {
    /**
     * 递归实现反转链表
     *
     * @param node
     */
    public static ListNode reverse(ListNode node) {
        if(node==null||node.next==null){
            return node;
        }
        return node;

    }

    public static void main(String[] args) {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(4);
        one.setNext(two);
        two.setNext(three);
        three.setNext(four);
    }


}
