package com.leetcode.reverseBetween;

import com.leetcode.ListNode;

import java.util.LinkedHashMap;

public class Solution1 {

    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyHead = new ListNode(-1);//虚节点
        dummyHead.next = head;
        ListNode pre = dummyHead;
        for (int i = 1; i < left; i++, pre = pre.next) {

        }
        //循环结束，找到left节点左边的元素pre

        ListNode tail = pre.next;//新链表的right位置对应的节点
        ListNode leftNode = new ListNode();//新链表right位置右边的位置
        pre.next = reverseList(pre.next, right - left, leftNode);
        tail.next=leftNode.next;
        return dummyHead.next;


    }

    /**
     * 翻转链表的前i+1个元素，返回翻转后的链表头结点
     *
     * @param head
     * @param i
     * @param leftNode 未发生翻转的节点起点位置
     * @return
     */
    private ListNode reverseList(ListNode head, int i, ListNode leftNode) {
        if(i==0||head==null||head.next==null){
            leftNode.next=head.next;
            return head;
        }
        ListNode pre=head;
        ListNode cur=head.next;
        while (i>0){
            ListNode next=cur.next;
            cur.next=pre;
            pre=cur;
            cur=next;
            i--;
        }
        leftNode.next=cur;
        return pre;

    }


    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.print("null");
        System.out.println();

    }

    public static ListNode createList(int[] arr) {

        if (arr == null || arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode cur = head;
        for (int i = 1; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;

        }
        return head;
    }


    public static void main(String[] args) {
        ListNode head = createList(new int[]{1, 2, 3, 4, 5});
        printList(head);
        Solution1 solution1=new Solution1();
        ListNode head2=solution1.reverseBetween(head,1,1);
        printList(head2);
        LinkedHashMap linkedHashMap=new LinkedHashMap();
    }
}
