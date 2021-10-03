package com.leetcode.oddEvenList;

import com.leetcode.ListNode;

public class Solution {
    public ListNode oddEvenList(ListNode head) {
        ListNode dummyHead1=new ListNode(-1);
        ListNode dummyHead2=new ListNode(-1);
        ListNode pre1=dummyHead1;
        ListNode pre2=dummyHead2;
        int count=0;
        for(ListNode cur=head;cur!=null;){
            count++;
            if(count%2==1){
                pre1.next=cur;
                pre1=pre1.next;
                cur=cur.next;
                pre1.next=null;
            }else {
                pre2.next=cur;
                pre2=pre2.next;
                cur=cur.next;
                pre2.next=null;
            }
        }
        pre1.next=dummyHead2.next;
        return dummyHead1.next;
    }
    public static void printList(ListNode head){
        while (head!=null){
            System.out.print(head.val+"->");
            head=head.next;
        }
        System.out.print("null");
        System.out.println();

    }


    public static ListNode createLinkedList(int[] arr) {
        if(arr==null||arr.length==0){
            return null;
        }
        ListNode head=new ListNode(arr[0]);
        ListNode cur=head;
        for (int i = 1; i < arr.length; i++) {
            cur.next=new ListNode(arr[i]);
            cur=cur.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Solution solution=new Solution();
        ListNode head=createLinkedList(new int[]{1,2,3,4,5});
        printList(head);
        ListNode head2=solution.oddEvenList(head);
        printList(head2);

    }
}
