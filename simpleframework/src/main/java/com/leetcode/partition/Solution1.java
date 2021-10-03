package com.leetcode.partition;

import com.leetcode.ListNode;

public class Solution1 {

    public ListNode partition(ListNode head, int x) {
        ListNode dummyHead1 = new ListNode(-1);//  虚拟头结点
        ListNode dummyHead2 = new ListNode(-1);// 虚拟头结点
        ListNode pre1 = dummyHead1;
        ListNode pre2 = dummyHead2;
        ListNode cur = head;
        while (cur != null) {
            ListNode next=cur.next;
            if (cur.val < x) {

                pre1.next=cur;
                cur.next=null;
                pre1=cur;
            } else {
                pre2.next=cur;
                cur.next=null;
                pre2=cur;
            }
            cur=next;
        }

        pre1.next = dummyHead2.next;//首位相连


        return dummyHead1.next;
    }

    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.print("null");
        System.out.println();

    }

    public static ListNode createList(int[] arr) {

        if (arr == null || arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode cur = head;
        for (int i = 1; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;

        }
        return head;
    }

    public static void main(String[] args) {
        ListNode head = createList(new int[]{8, 2, 3, 4, 1, 6});
        printList(head);
        Solution1 solution1 = new Solution1();
        ListNode head2 = solution1.partition(head, 3);
        printList(head2);
    }
}
