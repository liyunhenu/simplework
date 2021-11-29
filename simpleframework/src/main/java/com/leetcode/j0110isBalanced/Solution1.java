package com.leetcode.j0110isBalanced;

import com.leetcode.TreeNode;

public class Solution1 {

    public boolean isBalanced(TreeNode root) {

        if(root==null) return true;

        return depth(root)>0;

    }

    private int depth(TreeNode root) {
        if(root==null) return 0;

        int depthLeft=depth(root.left);
        int depthRight=depth(root.right);
        if(depthRight==-2||depthRight==-2||Math.abs(depthLeft-depthRight)>1){
            return -2;
        }
        return Math.max(depthLeft,depthRight)+1;
    }


}
