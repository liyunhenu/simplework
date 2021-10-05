package com.leetcode.j0102levelOrder;

import com.leetcode.TreeNode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        if (root == null) {
            return res;
        }
        queue.add(new Pair<>(root, 0));
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.getKey();
            Integer depth = pair.getValue();
            if (depth == res.size()) {
                res.add(new ArrayList<>());
            }
            ArrayList list = (ArrayList) res.get(depth);
            list.add(node.val);
            if (node.left != null) {
                queue.add(new Pair<>(node.left, depth + 1));
            }
            if (node.right != null) {
                queue.add(new Pair<>(node.right, depth + 1));
            }

        }
        return res;
    }

}
