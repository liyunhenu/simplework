package com.leetcode.reverseBetween;

import com.leetcode.ListNode;

public class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left >= right) {
            return head;
        }
        ListNode cur = head;
        ListNode leftleftNode = null;
        //leftleftNode.next=head;
        ListNode leftNode = null;
        ListNode rightrightNode;
        ListNode rightNode;
        int count = 0;
        while (cur != null) {
            count++;
            if (count == left - 1) {
                leftleftNode = cur;
            }
            if (count == left) {
                leftNode = cur;
            }
            if (count == right) {
                rightNode = cur;

                rightrightNode = cur.next;
                rightNode.next = null;//断掉连接
                if (leftleftNode != null) {
                    leftleftNode.next = reverseList(leftNode);
                } else {
                    head = reverseList(leftNode);
                }
                leftNode.next = rightrightNode;
                break;
            }

            cur = cur.next;
        }
        return head;

    }

    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;

            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode node = new ListNode(0);
        ListNode node1 = new ListNode(1);
        /*ListNode node2=new ListNode(2);
        ListNode node3=new ListNode(3);
        ListNode node4=new ListNode(4);
        ListNode node5=new ListNode(5);*/
        node.next = node1;
        /*node1.next=node2;
        node2.next=node3;
        node3.next=node4;
        node4.next=node5;*/

        ListNode head = node;
        printList(head);
        ListNode node6 = solution.reverseBetween(node, 1, 2);
        printList(node6);
    }

    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.println();

    }
}
