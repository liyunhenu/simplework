package com.leetcode.j0113pathSum;

import com.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {

        List<List<Integer>> resultAll = pathSum(root);
        List<List<Integer>> result = new ArrayList<>();
        if (resultAll != null && resultAll.size() > 0) {
            for (int i = 0; i < resultAll.size(); i++) {
                if (sum(resultAll.get(i)) == targetSum) {
                    result.add(resultAll.get(i));
                }
            }
        }
        return result;
    }

    private int sum(List<Integer> integers) {
        if (integers == null) return 0;
        int sum = 0;
        for (int i = 0; i < integers.size(); i++) {
            sum += integers.get(i);
        }
        return sum;
    }

    private List<List<Integer>> pathSum(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        if (root.left == null && root.right == null) {
            List<Integer> leaf = new ArrayList<>();
            leaf.add(root.val);
            result.add(leaf);
            return result;
        }
        List<List<Integer>> leftPath = pathSum(root.left);
        if (leftPath != null && leftPath.size() > 0) {
            for (int i = 0; i < leftPath.size(); i++) {
                leftPath.get(i).add(0, root.val);
            }
        }
        List<List<Integer>> rightPath = pathSum(root.right);
        if (rightPath != null && rightPath.size() > 0) {
            for (int i = 0; i < rightPath.size(); i++) {
                rightPath.get(i).add(0, root.val);
            }
        }
        result.addAll(leftPath);
        result.addAll(rightPath);
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

        Solution solution=new Solution();
        List<List<Integer>> result=solution.pathSum(node0,4);
        if(result!=null&&result.size()>0){
            for (int i = 0; i < result.size(); i++) {
                printf(result.get(i));
            }
        }
    }

    private static void printf(List<Integer> integers) {
        String begin="[";
        for (int i = 0; i < integers.size(); i++) {
            begin=begin+integers.get(i)+",";
        }
        begin=begin+"]";
        System.out.println(begin);
    }

}
