package com.juge.Josephus;

public class Main {


    //约瑟夫问题，n个难民，从1-k报数，报到k的将被杀掉，求最后一个幸存者编号
    public static int survival(int n, int k) {
        //1表示还活着，0表示被kill
        int sacrified = 0;
        Node head = new Node(1, 1);
        if(n==1){
           return head.value;
        }
        Node tail = head;
        for (int i = 2; i <= n; i++) {
            Node node = new Node(i, 1);
            tail.setNext(node);
            tail = node;
        }
        tail.next = head;
        //循环链
        int j = 1;
        Node node = head;
        Node luckBoy = head;//刚躲过处决的幸运儿
        while (sacrified < (n - 1)) {
            if (node.value == 1) {//活着
                if (j == k) {
                    node.setValue(0);//kill
                    System.out.println("kill "+node.index);
                    sacrified++;
                    j = 1;//计数器归0，开始下一轮
                } else {
                    luckBoy = node;
                    System.out.println(luckBoy.index);
                    j++;
                }
            }
            node = node.next;
        }
        return luckBoy.index;
    }


    public static class Node {

        int index;
        int value;
        Node next;

        public Node(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }


    public static void main(String[] args) {
        int n = 5;
        int k = 3;
        System.out.println("survival "+survival(41, 3));
    }

}
