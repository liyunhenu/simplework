package com.leetcode.j0111minDepth;

import com.leetcode.TreeNode;

class Solution {
    public int minDepth(TreeNode root) {
        if(root==null) return 0;
        else return minDepth1(root);

    }

    private int minDepth1(TreeNode root) {
        if(root==null) return Integer.MAX_VALUE;//减掉不是叶子节点的节点
        if(root.left==null && root.right==null) return 1;
        return Math.min(minDepth1(root.left),minDepth1(root.right))+1;
    }

    public static void main(String[] args) {
        TreeNode node0=new TreeNode(2);
        TreeNode node1=new TreeNode(3);
        TreeNode node2=new TreeNode(4);
        TreeNode node3=new TreeNode(5);
        TreeNode node4=new TreeNode(6);

        node0.right=node1;
        node1.right=node2;
        node2.right=node3;
        node3.right=node4;

        Solution solution=new Solution();
        System.out.println(solution.minDepth(node0));

        Solution1 solution1=new Solution1();
        System.out.println(solution1.minDepth(node0));
    }



}
