package com.leetcode.j0226invertTree;

import com.leetcode.TreeNode;

public class Solution {

    public TreeNode invertTree(TreeNode root) {

        if(root==null) return root;

        TreeNode left=invertTree(root.left);
        TreeNode right=invertTree(root.right);
        root.left=right;
        root.right=left;
        return root;
    }
}
