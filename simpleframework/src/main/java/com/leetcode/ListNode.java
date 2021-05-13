package com.leetcode;

public class ListNode {

    int val;
    ListNode next;

    public ListNode() {
    }

    ListNode(int x) {
        val = x;
        next=null;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return  val +"->";
    }
}
