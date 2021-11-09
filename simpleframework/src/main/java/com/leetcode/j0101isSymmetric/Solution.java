package com.leetcode.j0101isSymmetric;

import com.leetcode.TreeNode;

public class Solution {

    public boolean isSymmetric(TreeNode root) {
        if(root==null) return true;
        return isSymmetric(root,root);
    }

    private boolean isSymmetric(TreeNode root, TreeNode root1) {
        if(root==null&&root1==null) return true;
        if(root==null||root1==null) return false;
        if(root.val!=root1.val) return false;
        return isSymmetric(root.left,root1.right)&&isSymmetric(root.right,root1.left);
    }


    /*public static TreeNode invertTree(TreeNode root) {

        if(root==null) return root;

        TreeNode left=invertTree(root.left);
        TreeNode right=invertTree(root.right);
        root.left=right;
        root.right=left;
        return root;
    }
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null&&q==null) return true;
        if(p==null||q==null) return false;
        if(p.val!=q.val) return false;
        return isSameTree(p.left,q.left)&&isSameTree(p.right,q.right);
    }*/


    public static void main(String[] args) {
        TreeNode root=new TreeNode(1);
        TreeNode node0=new TreeNode(2);
        TreeNode node1=new TreeNode(2);
        TreeNode node2=new TreeNode(3);
        TreeNode node3=new TreeNode(3);
        root.left=node0;
        root.right=node1;
        node0.right=node2;
        node1.right=node3;
        Solution solution=new Solution();
        System.out.println(solution.isSymmetric(root));
    }
}
