package com.leetcode.j0199rightSideView;

import com.leetcode.TreeNode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null) {
            return res;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            int size=queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node=queue.poll();
                if(i==(size-1)){
                   res.add(node.val);
                }
                if(node.left!=null) queue.add(node.left);
                if(node.right!=null) queue.add(node.right);
            }
        }
        return res;

    }
}
