package com.leetcode;

import java.util.HashSet;

/**
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

public class Question3 {
    /**
     * 从左游表开始，右游标往右找最长序列右边界位置，hashSet记录记录是不是字符重复出现
     * 左游标右移，重复上述步骤
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int result = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {
            int j = i;
            HashSet<Character> set = new HashSet<>();
            for (; j < length; j++) {
                if (!set.contains(s.charAt(j))) {
                    Character ch = s.charAt(j);
                    set.add(s.charAt(j));
                } else {
                    break;
                }
            }
            result = Math.max(result, j - i );

        }

        return result;
    }

    /**
     * 以当前位置为窗口右边界，找该位置字符，往左上次出现的位置,左边界位置是上次字符出现位置和当前左边界的最大值，当前左边界是所有字符曾经的最后出现位置。
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring1(String s) {
        int result = 0;
        int[] last=new int[256];
        int left=0;
        for (int i = 0; i < s.length(); i++) {
            int index=s.charAt(i);
            left=Math.max(left,last[index]);
            int length=i-left+1;
            result=Math.max(result,length);
            last[index]=i+1;//+1是针对出现过的字符，多加1，在计算length的时候会补回来，刚好计算左窗口的位置的时候，本来应该+1的，这里刚好last[index]多算1，就不用了
        }
        return result;
    }

    /**
     * 还是这种比较好理解，last[i]初始化为-1，该位置没出现过，就和字符出现位置是0的区分开了
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring2(String s) {
        // 记录字符上一次出现的位置
        int[] last = new int[128];
        for (int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int n = s.length();

        int res = 0;
        int start = 0; // 窗口开始位置
        for (int i = 0; i < n; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res = Math.max(res, i - start + 1);
            last[index] = i;
        }

        return res;
    }



    public static void main(String[] args) {
        String s = new String("pwwkew");
        System.out.println(lengthOfLongestSubstring2(s));
    }
}
