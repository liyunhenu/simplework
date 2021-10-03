package com.leetcode.j0147insertionSortList;

import com.leetcode.ListNode;
import com.leetcode.ListNodeUtil;

public class Solution {

    public ListNode insertionSortList(ListNode head) {

        ListNode dummyHead = new ListNode(Integer.MIN_VALUE);
        ListNode cur = null;
        dummyHead.next = cur;

        ListNode p = head;
        while (p != null) {
            cur = p;
            p = p.next;
            cur.next = null;
            insert(dummyHead, cur);
        }
        return dummyHead.next;
    }

    public void insert(ListNode head, ListNode p) {
        ListNode cur = head;
        while (cur.next != null) {
            if (p.val < cur.next.val) {
                //
                p.next = cur.next;
                cur.next = p;
                break;
            } else {
                cur=cur.next;
            }

        }
        cur.next = p;
    }


    public static void main(String[] args) {
        ListNode head = ListNodeUtil.createLinkedList(new int[]{1});
        ListNodeUtil.printList(head);
        Solution solution=new Solution();
        ListNodeUtil.printList(solution.insertionSortList(head));
    }
}
