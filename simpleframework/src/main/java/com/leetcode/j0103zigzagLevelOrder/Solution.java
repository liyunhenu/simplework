package com.leetcode.j0103zigzagLevelOrder;

import com.leetcode.TreeNode;
import javafx.util.Pair;

import java.util.*;

public class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        if (root == null) {
            return new ArrayList<>();
        }
        queue.add(new Pair<>(root, 0));
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.getKey();
            Integer depth = pair.getValue();
            if (depth == res.size()) {
                res.add(new LinkedList<>());
            }
            LinkedList list = (LinkedList) res.get(depth);
            if(depth%2==0){
                list.addLast(node.val);
            }else{
                list.addFirst(node.val);
            }

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
