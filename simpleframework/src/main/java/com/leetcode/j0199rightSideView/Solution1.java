package com.leetcode.j0199rightSideView;

import com.leetcode.TreeNode;
import javafx.util.Pair;

import java.util.*;

public class Solution1 {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root==null) return res;
        Map<Integer,Integer> rightView=new HashMap<>();
        Stack<Pair<TreeNode,Integer>> stack=new Stack<Pair<TreeNode, Integer>>();
        stack.push(new Pair<>(root,0));
        Integer maxDepth=0;
        while (!stack.isEmpty()){
            Pair pair=stack.pop();
            TreeNode node= (TreeNode) pair.getKey();
            Integer depth= (Integer) pair.getValue();
            maxDepth=Math.max(depth, maxDepth);
            if(!rightView.containsKey(depth)){
                rightView.put(depth,node.val);
            }
            if(node.left!=null){stack.push(new Pair<>(node.left,depth+1));}
            if(node.right!=null){stack.push(new Pair<>(node.right,depth+1));}

        }
        for(int i=0;i<=maxDepth;i++){
            res.add(rightView.get(i));
        }

        return res;

    }
}
