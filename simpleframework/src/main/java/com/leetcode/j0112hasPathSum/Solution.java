package com.leetcode.j0112hasPathSum;

import com.leetcode.TreeNode;

/**
 * 输入：root = [], targetSum = 0
 * 输出：false
 * 解释：由于树是空的，所以不存在根节点到叶子节点的路径。
 */
public class Solution {

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null) return false;
        if(root.left==null&& root.right==null) return root.val==targetSum;
        return hasPathSum(root.left,targetSum-root.val)||hasPathSum(root.right,targetSum-root.val);
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
        System.out.println(solution.hasPathSum(node0,5));
    }
}
