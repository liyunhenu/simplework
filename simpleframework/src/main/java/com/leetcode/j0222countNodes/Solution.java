package com.leetcode.j0222countNodes;

import com.leetcode.TreeNode;

public class Solution {
    public int countNodes(TreeNode root) {

        if (root==null) return 0;

        return countNodes(root.left)+countNodes(root.right)+1;

    }
}
