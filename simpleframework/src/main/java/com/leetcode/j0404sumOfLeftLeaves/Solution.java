package com.leetcode.j0404sumOfLeftLeaves;

import com.leetcode.TreeNode;

public class Solution {

    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) return sumOfLeftLeaves(root.right);
        if (root.right == null) {
            return sumOfLeft(root.left);
        }
        return sumOfLeft(root.left) + sumOfLeftLeaves(root.right);
    }

    //
    private int sumOfLeft(TreeNode node) {
        if (node.left == null && node.right == null) return node.val;
        return sumOfLeftLeaves(node);
    }

    public static void main(String[] args) {
        TreeNode node0=new TreeNode(2);
        TreeNode node1=new TreeNode(3);
        TreeNode node2=new TreeNode(4);
        TreeNode node3=new TreeNode(5);
        TreeNode node4=new TreeNode(6);

        node0.left=node1;
        node1.left=node2;
        node2.left=node3;
        node3.left=node4;

        Solution solution=new Solution();
        System.out.println(solution.sumOfLeftLeaves(node0));
    }
}
