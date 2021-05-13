package com.leetcode;


/**
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 * <p>
 * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * <p>
 * 你可以假设 nums1 和 nums2 不会同时为空。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Question4 {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {

        return findMedianSortedArrays(nums1, 0, nums2, 0);

    }

    public static double findMedianSortedArrays(int[] nums1, int position1, int[] nums2, int position2) {
        int kprime = (nums1.length + nums2.length + 1) / 2;//k是中位数
        int k = kprime - position1 - position2;//k是中位数

        /*if(k==1){
          int i=nums1[nums1.length-position1]
        }*/

        if (position1 == nums1.length) {
            return nums2[k];
        }
        if (position2 == nums2.length) {
            return nums1[k];
        }
        //寻找第k个元素，
        if (nums1.length - position1 >= (k / 2 - 1) && nums2.length - position2 >= (k / 2 - 1)) {
            if (nums1[k / 2 - 1 + position1] <= nums2[k / 2 - 1 + position2]) {
                position1 = position1 + k / 2;

            } else {
                position2 = position2 + k / 2;
            }
        } else if (nums1.length - position1 < (k / 2 - 1)) {
            position1 = nums1.length;
        } else if (nums2.length - position2 < (k / 2 - 1)) {
            position2 = nums2.length;
        }
        return findMedianSortedArrays(nums1, position1, nums2, position2);
    }

    /**
     * @param nums1
     * @param start1
     * @param nums2
     * @param start2
     * @param position 要查找第几个元素
     * @return
     */
    public static int findElement(int[] nums1, int start1, int[] nums2, int start2, int position) {
        //
        if (nums1.length <=start1) {
            return nums2[position + start2-1];
        }

        if (nums2.length<= start2) {
            return nums1[position + start1-1];
        }

        if(position==1){
            return Math.min(nums1[start1],nums2[start2]);
        }
        //长度不够
        if ((nums1.length - start1) < position / 2) {
            if (nums1[nums1.length - 1] < nums2[start2 + nums1.length - start1 - 1]) {
                return findElement(nums1, nums1.length, nums2, start2, position - (nums1.length - start1));
            } else {
                return findElement(nums1, start1, nums2, start2 + nums1.length - start1, position - (nums1.length - start1));
            }
        } else if ((nums2.length - start2) < position / 2) {
            if (nums2[nums2.length - 1] < nums1[start1 + nums2.length - start2 - 1]) {
                return findElement(nums1, start1, nums2, nums2.length, position - (nums2.length - start2));
            } else {
                return findElement(nums1, start1 + nums2.length - start2, nums2, start2, position - (nums2.length - start2));
            }
        }

        //
        if (nums1[position / 2 + start1-1] < nums2[position / 2 + start2-1]) {
            return findElement(nums1, start1 + position / 2, nums2, start2, position - position / 2);

        } else {
            return findElement(nums1, start1, nums2, start2 + position / 2, position - position / 2);
        }
    }

    public static double findMedianSortedArrays3(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        if ((length1 + length2) % 2 == 0) {
            int s1 = findElement(nums1, 0, nums2, 0, (length1 + length2) / 2);
            int s2 = findElement(nums1, 0, nums2, 0, (length1 + length2) / 2 +1);
            return (s1 + s2) / 2.0;
        } else {
            return (double) findElement(nums1, 0, nums2, 0, (length1 + length2) / 2);
        }

    }


    /**
     * 暴力解法，归并排序，排序后的数组找中位数,算法复杂度O(m+n),达不到O(log(m+n))
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int[] r1 = new int[nums1.length + 1];
        int[] k1 = new int[nums2.length + 1];
        int[] nums = new int[nums1.length + nums2.length];
        for (int i = 0; i < nums1.length; i++) {
            r1[i] = nums1[i];
        }
        r1[nums1.length] = Integer.MAX_VALUE;
        for (int i = 0; i < nums2.length; i++) {
            k1[i] = nums2[i];
        }
        k1[nums2.length] = Integer.MAX_VALUE;
        int j = 0;
        int n = 0;
        for (int i = 0; i < nums.length; i++) {
            if (r1[j] < k1[n]) {
                nums[i] = r1[j];
                j++;
            } else {
                nums[i] = k1[n];
                n++;
            }
        }
        double result = 0.0;
        result = (double) (nums[nums.length / 2] + nums[(nums.length - 1) / 2]) / 2;
        return result;

    }


    public static void main(String[] args) {

        /*int[] nums1 = new int[]{1, 2, 3, 4};
        int[] nums2 = new int[]{7, 8, 9, 10, 11, 12};
        Double middle = findMedianSortedArrays1(nums1, nums2);
        System.out.println(middle);*/
        int[] nums1 = new int[]{1};
        int[] nums2 = new int[]{2,3,4,5,6};
        Double middle = findMedianSortedArrays3(nums1, nums2);
        System.out.println(middle);

    }
}
