package com.leetcode.reverseList;

import com.leetcode.ListNode;

public class Solution {
    public ListNode reverseList(ListNode head) {

        ListNode pre=null;
        ListNode cur=head;
        while (cur!=null){
            ListNode next=cur.next;

            cur.next=pre;

            pre=cur;
            cur=next;
        }
        return pre;
    }




    public static void main(String[] args) {
        Solution solution=new Solution();
        /*ListNode node=new ListNode(0);
        ListNode node1=new ListNode(1);
        ListNode node2=new ListNode(2);
        ListNode node3=new ListNode(3);
        ListNode node4=new ListNode(4);
        ListNode node5=new ListNode(5);
        node.next=node1;
        node1.next=node2;
        node2.next=node3;
        node3.next=node4;
        node4.next=node5;*/

        ListNode head=createLinkedList(new int[]{1});
        printList(head);
        ListNode node6=solution.reverseList(head);
        printList(node6);

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
