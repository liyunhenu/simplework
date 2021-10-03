package com.leetcode.j0082deleteDuplicates;

import com.leetcode.ListNode;
import com.leetcode.ListNodeUtil;

import java.util.HashSet;
import java.util.Set;

public class Solution1 {

    public ListNode deleteDuplicates(ListNode head) {

        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;

        ListNode pre=dummyHead;
        for(ListNode cur=pre.next;cur!=null;){
            ListNode p=cur;//记录重复指针
            int num=0;
            while (p!=null&&p.val==cur.val){
                num++;
                p=p.next;
            }
            if(num>1){
                pre.next=p;
            }else {
                pre=cur;
            }
            cur=pre.next;
        }

        return dummyHead.next;
    }


    public static void main(String[] args) {
        Solution1 solution1=new Solution1();
        ListNode head= ListNodeUtil.createLinkedList(new int[]{1,2,3,3,4,4,5});
        ListNodeUtil.printList(head);
        ListNodeUtil.printList(solution1.deleteDuplicates(head));
    }
}
