package com.leetcode.j0111minDepth;

import com.leetcode.TreeNode;

public class Solution1 {

    public int minDepth(TreeNode root) {
        if(root==null) return 0;
        if(root.left==null){return minDepth(root.right)+1;}//减掉伪叶子节点
        if(root.right==null) return minDepth(root.left)+1;//减掉伪叶子节点
        else return Math.min(minDepth(root.left),minDepth(root.right))+1;

    }



}
