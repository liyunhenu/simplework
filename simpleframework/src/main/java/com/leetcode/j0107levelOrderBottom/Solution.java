package com.leetcode.j0107levelOrderBottom;

import com.leetcode.TreeNode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res=new ArrayList<>();
        Queue<Pair<TreeNode,Integer>> queue=new LinkedList<>();
        if( root==null) return res;

        queue.add(new Pair(root,0));
        while (!queue.isEmpty()){
            Pair pair=queue.poll();
            TreeNode node= (TreeNode) pair.getKey();
            Integer depth= (Integer) pair.getValue();
            if(depth==res.size()){
                res.add(0,new ArrayList<>());
            }
            ArrayList<Integer> arrayList= (ArrayList<Integer>) res.get(res.size()-1-depth);
            arrayList.add(node.val);
            if(node.left!=null) queue.add(new Pair(node.left,depth+1));
            if(node.right!=null) queue.add(new Pair<>(node.right,depth+1));
        }

        return res;
    }
}
