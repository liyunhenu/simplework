package com.leetcode.deleteDuplicates;

import com.leetcode.ListNode;

class Solution {
    public ListNode deleteDuplicates(ListNode head) {

        ListNode cur = head;
        int preValue = 101;
        ListNode preNode = head;
        while (cur != null) {
            int curValue = cur.val;
            if (curValue == preValue) {
                //
                preNode.next = cur.next;
                cur.next = null;
                cur=preNode;//
            }
            preNode = cur;
            preValue=preNode.val;
            cur = cur.next;
        }
        return head;

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
        ListNode head=createLinkedList(new int[]{1,1,2,2,3,4,5});
        printList(head);
        solution.deleteDuplicates(head);
        printList(head);
    }
}
