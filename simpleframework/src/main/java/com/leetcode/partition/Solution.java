package com.leetcode.partition;

import com.leetcode.ListNode;

class Solution {
    public ListNode partition(ListNode head, int x) {
        return null;
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
}
