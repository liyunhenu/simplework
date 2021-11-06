package com.leetcode.j0023mergeKLists;

import com.leetcode.ListNode;

import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    /**
     * 常规解法n*K
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        for (int i = 0; i < lists.length; i++) {
            ListNode dummyHead = new ListNode(-1);
            dummyHead.next = lists[i];
            lists[i] = dummyHead;
        }
        ListNode headAll = new ListNode(-1);
        ListNode current = headAll;
        int exitCount;
        while (true) {
            int minValue = Integer.MAX_VALUE;
            exitCount = 0;
            int minIndex = 0;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i].next == null) {
                    exitCount++;
                    if (exitCount == lists.length) {
                        return headAll.next;
                    }
                } else {
                    if (minValue > lists[i].next.val) {
                        minIndex = i;
                        minValue = lists[i].next.val;
                    }
                }
            }
            if(exitCount==lists.length-1){
                //只剩下一个链表有元素
                current.next=lists[minIndex].next;
                lists[minIndex].next=null;
                return headAll.next;
            }else {
                ListNode next = lists[minIndex].next;
                lists[minIndex].next = next.next;
                next.next = null;
                current.next = next;
                current = current.next;
            }


        }
    }


    public static void main(String[] args) {
        //lists = [[1,4,5],[1,3,4],[2,6]]
        ListNode head1 = createList(new int[]{1, 4, 5,6,7,8,9,10});
        printListNode(head1);
        ListNode head2 = createList(new int[]{1, 3, 4});
        printListNode(head2);
        ListNode head3 = createList(new int[]{2, 6});
        printListNode(head3);
        ListNode[] lists = new ListNode[]{head1, head2, head3};
        Solution solution = new Solution();
        ListNode result = solution.mergeKLists(lists);
        printListNode(result);
    }

    private static void printListNode(ListNode head1) {
        /*ListNode dummyHead=new ListNode(-1);
        dummyHead.next=head1;*/
        ListNode current = head1;
        while (current != null) {
            System.out.print(current.val + "-->");
            current = current.next;
        }
        System.out.print("null");
        System.out.println();
    }

    private static ListNode createList(int[] ints) {
        if (ints == null || ints.length == 0) {
            return null;
        }
        ListNode head = new ListNode(ints[0]);
        ListNode current = head;
        for (int i = 1; i < ints.length; i++) {
            ListNode node = new ListNode(ints[i]);
            current.next = node;
            current = current.next;
        }
        return head;
    }
}
