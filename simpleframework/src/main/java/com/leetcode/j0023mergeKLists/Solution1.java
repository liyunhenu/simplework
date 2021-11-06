package com.leetcode.j0023mergeKLists;

import com.leetcode.ListNode;
import com.leetcode.ListNodeUtil;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution1 {


    /**
     * 优先队列，数组n个元素的寻找最小值，变成了优先队列的最小值，降低为log(n)级别了
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {

        if(lists==null||lists.length==0){return null;}
        if(lists.length==1) return lists[0];
        ListNode dummyHead=new ListNode(-1);
        ListNode current=dummyHead;


        Comparator<ListNode> comparator=new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val-o2.val;
            }
        };

        PriorityQueue<ListNode> priorityQueue=new PriorityQueue<>(comparator);
        for (int i = 0; i < lists.length; i++) {
            priorityQueue.offer(lists[i]);
        }
        while (priorityQueue.size()>0){
            ListNode nextNode=priorityQueue.poll();
            if(nextNode.next!=null){
                priorityQueue.offer(nextNode.next);
            }
            current.next=nextNode;
            nextNode.next=null;
            current=current.next;

        }
        return dummyHead.next;


    }

    public static void main(String[] args) {
        //lists = [[1,4,5],[1,3,4],[2,6]]
        ListNode head1 = ListNodeUtil.createLinkedList(new int[]{1, 4, 5,6,7,8,9,10});
        ListNodeUtil.printList(head1);
        ListNode head2 = ListNodeUtil.createLinkedList(new int[]{1, 3, 4});
        ListNodeUtil.printList(head2);
        ListNode head3 = ListNodeUtil.createLinkedList(new int[]{2, 6});
        ListNodeUtil.printList(head3);
        ListNode[] lists = new ListNode[]{head1, head2, head3};
        Solution1 solution = new Solution1();
        ListNode result = solution.mergeKLists(lists);
        ListNodeUtil.printList(result);
    }



}
