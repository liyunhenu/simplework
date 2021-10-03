package com.leetcode.partition;

import com.leetcode.ListNode;

class Solution {
    public ListNode partition(ListNode head, int x) {
        int count=0;

        ListNode cur=head;
        ListNode tail=head;
        while (cur!=null){
            count++;
            if(cur.next==null){
                tail=cur;
            }
            cur=cur.next;
        }
        //if(count==1) return head;
        System.out.println(count);
        ListNode preNode=new ListNode(1000);//虚拟头结点
        ListNode oldPre=preNode;
        preNode.next=head;
        cur=head;
        while (count>0){
            if (cur.val < x) {
                preNode=cur;
            } else {
                if(cur==tail) break;
                preNode.next=cur.next;//前一个节点的跳过当前节点
                cur.next=null;//当前节点断开与后续节点连接
                tail.next=cur;//当前节点放到尾部
                tail=cur;//更新尾部节点

                cur=preNode;//重新定位当前遍历节点位置
            }
            cur = cur.next;
            count--;
        }
        return oldPre.next;
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
        ListNode head=createLinkedList(new int[]{1});
        printList(head);
        ListNode head2=solution.partition(head,0);
        printList(head2);

    }
}
