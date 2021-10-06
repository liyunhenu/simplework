package com.leetcode.j0199rightSideView;

import com.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {
    private ArrayList<Integer> list=new ArrayList<>();

    public List<Integer> rightSideView(TreeNode root) {

        dfs(root,0);
        return list;
    }

    private void dfs(TreeNode root, int depth) {
        if(root==null) return;
        if(list.size()==depth){
            list.add(root.val);
        }
        dfs(root.right,depth+1);
        dfs(root.left,depth+1);
    }
}
