package com.leetcode.j0257binaryTreePaths;

import com.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        //递归终止条件 1.本身是null 2.叶子节点
        if (root == null) return result;
        if (root.left == null && root.right == null) {
            result.add(String.valueOf(root.val));
            return result;
        }
        //非叶子节点
        List<String> leftList = binaryTreePaths(root.left);
        if (leftList != null && leftList.size() > 0) {
            for (int i = 0; i < leftList.size(); i++) {
                result.add(root.val + "->" + leftList.get(i));
            }
        }
        List<String> rightList = binaryTreePaths(root.right);
        if (rightList != null && rightList.size() > 0) {
            for (int i = 0; i < rightList.size(); i++) {
                result.add(root.val + "->" + rightList.get(i));
            }
        }
        return result;

    }

    public static void main(String[] args) {
        TreeNode node0 = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);

        node0.left = node1;
        node0.right = node2;
        node1.right = node4;
        // node3.right=node4;
        Solution solution = new Solution();
        List<String> result = solution.binaryTreePaths(node0);
        if (result != null && result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                System.out.println(result.get(i));
            }
        }
    }


}
