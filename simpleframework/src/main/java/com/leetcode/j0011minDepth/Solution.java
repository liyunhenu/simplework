package com.leetcode.j0011minDepth;

import com.leetcode.TreeNode;

class Solution {
    public int minDepth(TreeNode root) {
        if(root==null) return 0;
        if(root.left==null||root.right==null) return 1;
        return Math.min(minDepth(root.left),minDepth(root.right))+1;
    }



}
