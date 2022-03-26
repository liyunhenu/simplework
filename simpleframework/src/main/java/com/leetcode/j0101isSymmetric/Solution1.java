package com.leetcode.j0101isSymmetric;

import com.leetcode.TreeNode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Solution1 {

    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(root);
        while (queue.size() > 0) {
            TreeNode node0 = queue.poll();
            TreeNode node1 = queue.poll();
            if (node0==null&&node1==null) {

            }else if(node0==null||node1==null){
                return false;
            }else{
                if(node0.val!=node1.val){return false;}
                queue.offer(node0.left);
                queue.offer(node1.right);
                queue.offer(node0.right);
                queue.offer(node1.left);
            }
        }

        return true;

    }


    public static void main(String[] args) {
        /*TreeNode root = new TreeNode(1);
        TreeNode node0 = new TreeNode(2);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(3);
        root.left = node0;
        root.right = node1;
        node0.right = node2;
        node1.right = node3;
        Solution1 solution = new Solution1();
        System.out.println(solution.isSymmetric(root));*/


        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date=new Date();
        System.out.println(format.format(date));
    }

}
