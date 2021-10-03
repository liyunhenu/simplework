package com.leetcode.j0082deleteDuplicates;

import com.leetcode.ListNode;
import com.leetcode.ListNodeUtil;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public ListNode deleteDuplicates(ListNode head) {

        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        Set<Integer> duplicatesSet = new HashSet<>();
        for (ListNode cur = dummyHead; cur.next != null; ) {
            if (cur.next.next != null && cur.next.next.val == cur.next.val) {
                duplicatesSet.add(cur.next.val);
            }
            if (duplicatesSet.contains(cur.next.val)) {
                ListNode deleteNode = cur.next;
                cur.next = cur.next.next;
                deleteNode.next = null;
            } else {
                cur = cur.next;
            }
        }
        return dummyHead.next;
    }



    public static void main(String[] args) {
        ListNode head = ListNodeUtil.createLinkedList(new int[]{1, 1, 1, 2, 2});
        ListNodeUtil.printList(head);
        Solution solution = new Solution();
        ListNodeUtil.printList(solution.deleteDuplicates(head));
    }
}
