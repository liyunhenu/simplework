package com.leetcode.j0203removeElements;

import com.leetcode.ListNode;
import com.leetcode.ListNodeUtil;

/**
 * 0203
 */
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummyHead=new ListNode(-1);
        dummyHead.next=head;
        for(ListNode cur=dummyHead;cur!=null;){
            ListNode next=cur.next;
            if(next!=null&&next.val==val){
                cur.next=next.next;
                next.next=null;
            }else {
                cur=cur.next;
            }
        }

      return dummyHead.next;
    }


    public static void main(String[] args) {
        ListNode head= ListNodeUtil.createLinkedList(new int[]{1,2,3,4,5,6,6,3,7});
        ListNodeUtil.printList(head);
        Solution solution=new Solution();
        ListNodeUtil.printList(solution.removeElements(head,6));
    }

}
