package com.leetcode.j0147insertionSortList;

import com.leetcode.ListNode;
import com.leetcode.ListNodeUtil;

public class Solution1 {

    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode lastSortedNode = head;
        ListNode curr = head.next;
        while (curr!=null){
            if(lastSortedNode.val<=curr.val){
                lastSortedNode.next=curr;
                lastSortedNode=curr;
            }else {
                ListNode pre=dummyHead;
                while (pre.next!=null&&pre.next.val<=curr.val){
                    pre=pre.next;
                }
                lastSortedNode.next=curr.next;
                curr.next=pre.next;
                pre.next=curr;
            }

            curr=lastSortedNode.next;


        }
        return dummyHead.next;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        /*ListNode head = ListNodeUtil.createLinkedList(new int[]{-1,5,3,4,0});
        ListNodeUtil.printList(head);
        Solution1 solution1 = new Solution1();
        ListNodeUtil.printList(solution1.insertionSortList(head));*/

        String str6="goodmorning";
        String str5=new String("good")+new String("morning");
        str5.intern();

        System.out.println("str5==str6"+(str5==str6)+"str5==str5.intern()"+(str5==str5.intern()));


        Class myClass=Class.forName("java.lang.String");
        System.out.println(myClass.getPackage().getName());

    }
}
