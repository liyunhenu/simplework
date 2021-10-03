package com.leetcode.j0021mergeTwoLists;

import com.leetcode.ListNode;
import com.leetcode.ListNodeUtil;

public class Solution {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null) return l2;
        if(l2==null) return l1;
        ListNode dummyHead1 = new ListNode(-1);
        ListNode dummyHead2 = new ListNode(-1);
        dummyHead1.next = l1;
        dummyHead2.next = l2;
        ListNode cur1 = dummyHead1;
        ListNode cur2 = dummyHead2;
        ListNode newHaed = new ListNode(-1);
        ListNode cur = newHaed;
        while (cur1.next != null && cur2.next != null) {
            if (cur1.next != null && cur2.next != null) {
                if (cur1.next.val <= cur2.next.val) {
                    cur.next = cur1.next;
                    cur1.next = cur1.next.next;
                } else {
                    cur.next = cur2.next;
                    cur2.next = cur2.next.next;
                }

            }
            cur = cur.next;
            cur.next=null;

        }
        if(cur1.next!=null){
            cur.next=cur1.next;
        }
        if(cur2.next!=null){
            cur.next=cur2.next;

        }
        return newHaed.next;
    }


    public static void main(String[] args) {
        ListNode head1 = ListNodeUtil.createLinkedList(new int[]{0,1,1,2});
        ListNode head2 = ListNodeUtil.createLinkedList(new int[]{});
        ListNodeUtil.printList(head1);
        ListNodeUtil.printList(head2);
        Solution solution=new Solution();
        ListNodeUtil.printList(solution.mergeTwoLists(head1,head2));

    }
}
