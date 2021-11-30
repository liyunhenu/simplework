package com.leetcode.j0222countNodes;

import com.leetcode.TreeNode;

public class Solution1 {

    public int countNodes(TreeNode root) {


        /**
         * 思路：利用二叉树是完全二叉树的特
         * 首先深度遍历完全二叉树的左子树，找到二叉树的深度 n
         * 那么二叉树至少有 2^（n-1）个节点   满二叉树的话可能是2^n -1个节点
         * 具体是多少，二分查找，沿着root根节点往左右找 根节点往右走意味着节点个数越多，从root阶段出发，选择左节点意味着0 选择右意味着1
         * 从高位找到低位
         *
         * */

        if(root==null) return 0;
        int high=1;//二叉树的深度
        TreeNode curr=root;
        while(curr.left!=null){
            high++;
            curr=curr.left;
        }
        int minCount=1<<(high-1);//完全最少可能个数
        int maxCount=(1<<high)-1;//完全二叉树最大可能个数
        //二分查找 完全二叉树可能的个数，判断依据完全二叉树的特性，左右儿子对应0 1
        //int middle=(maxCount - minCount + 1) / 2 + minCount;
        int middle=(maxCount +minCount + 1) / 2 ;//middle不能直接(maxCount +minCount)/2 因为这样的话在min和max差1的时候，middle没有步进值

        while (minCount<maxCount){
            if(isExist(root,high,middle)){
                minCount=middle;
            }else {
                maxCount=middle-1;
            }
            middle=(maxCount +minCount + 1) / 2 ;
        }

        return middle;//当区间限定，左边界=右边界的时候，锁定真实个数

    }

    private boolean isExist(TreeNode root, int high, int middle) {
        int bits=1<<(high-2);
        while (root!=null&&bits>0){
            if((middle&bits)==0){
                root=root.left;
            }else {
                root=root.right;
            }
            bits>>=1;
        }
        return root!=null;
    }


    public static void main(String[] args) {
        TreeNode node1=new TreeNode(1);
        TreeNode node2=new TreeNode(2);
        TreeNode node3=new TreeNode(3);
        TreeNode node4=new TreeNode(4);
        TreeNode node5=new TreeNode(5);
        TreeNode node6=new TreeNode(6);
        node1.left=node2;
        node1.right=node3;
        node2.left=node4;
        node2.right=node5;
        node3.left=node6;
        /*Solution solution=new Solution();
        System.out.println(solution.countNodes(node1));*/
        Solution1 solution1=new Solution1();
        System.out.println(solution1.countNodes(node1));


    }

}
