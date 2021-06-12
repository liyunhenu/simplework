package com.leetcode.addtwonumbers;


/**
 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。

 请你将两个数相加，并以相同形式返回一个表示和的链表。

 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/add-two-numbers
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 **/

public class Solution{


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode node=new ListNode();
        ListNode head=node;
        int more=0;//进位
        while(l1!=null||l2!=null||more!=0){

            int plusValue=0;
            if(l1!=null&&l2!=null){
                plusValue=l1.val+l2.val+more;

                l1=l1.next;
                l2=l2.next;

            }else if(l1==null&&l2==null){
                plusValue=more;
            }else{
                if(l1!=null){
                    plusValue+=l1.val+more;
                    l1=l1.next;
                }
                if(l2!=null){
                    plusValue+=l2.val+more;
                    l2=l2.next;
                }
            }
            if(plusValue>=10){
                plusValue=plusValue-10;
                more=1;
            }else {
                more=0;
            }

            ListNode newNode=new ListNode(plusValue);
            node.next=newNode;
            node=node.next;
        }
        return head.next;

    }

    private static class ListNode{

        int val;
        ListNode next;

        ListNode(){
        }

        ListNode(int value){
            this.val=value;
        }

        ListNode(int value,ListNode node){
            this.val=value;
            this.next=node;
        }

        public int getValue(){
            return this.val;
        }


        public ListNode getNext(){
            return this.next;
        }

    }


    public static void main(String[] args){
        ListNode node0=new ListNode(5);
        ListNode node1=new ListNode(2,node0);
        ListNode node2=new ListNode(3,node1);
        ListNode node3=new ListNode(9,node2);
        ListNode num1=node3;
        ListNode node5=new ListNode(5);
        ListNode node6=new ListNode(2,node5);
        ListNode node7=new ListNode(3,node6);
        ListNode node8=new ListNode(9,node7);
        ListNode num2=node8;
        ListNode result=addTwoNumbers(num1,num2);

        while (result!=null){
            System.out.print(result.val+"->");
            result=result.next;
        }
        System.out.println();

        /*ListNode node9=new ListNode(2);
        ListNode node10=new ListNode();
        ListNode node11=new ListNode();
        ListNode node12=new ListNode();*/


    }

}
