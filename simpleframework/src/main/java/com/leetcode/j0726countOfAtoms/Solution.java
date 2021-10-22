package com.leetcode.j0726countOfAtoms;

import java.util.*;

public class Solution {

    int i;
    int n;
    String formula;

    public String countOfAtoms(String formula) {

        this.i = 0;
        this.n = formula.length();
        this.formula = formula;
        Deque<Map<String, Integer>> stack = new LinkedList<>();
        stack.push(new HashMap<String, Integer>());
        while (i < n) {
            Character tmp = formula.charAt(i);
            if (tmp == '(') {
                stack.push(new HashMap<>());
                i++;
            } else if (tmp == ')') {
                i++;
                Integer num = parseNumber();
                HashMap<String, Integer> popMap = (HashMap<String, Integer>) stack.pop();
                HashMap<String, Integer> peekMap = (HashMap<String, Integer>) stack.peek();
                Iterator iterator = popMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
                    String key = entry.getKey();
                    Integer val = entry.getValue();
                    peekMap.put(key, peekMap.getOrDefault(key, 0) + val * num);
                }
            } else if (Character.isUpperCase(tmp)) {
                String atom = parseAtom();
                Integer num = parseNumber();
                HashMap<String, Integer> peekMap = (HashMap<String, Integer>) stack.peek();
                peekMap.put(atom, peekMap.getOrDefault(atom, 0) + num);

            }

        }
        HashMap<String, Integer> peek = (HashMap<String, Integer>) stack.peek();
        TreeMap<String, Object> treeMap = new TreeMap<>(peek);
        StringBuilder sb = new StringBuilder();

        Iterator iterator = treeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            sb.append(entry.getKey());
            if ((Integer) entry.getValue() > 1) {
                sb.append(entry.getValue());
            }
        }


        return sb.toString();


    }

    public int parseNumber() {

        if (i >= n || !Character.isDigit(formula.charAt(i))) {
            return 1;
        }
        StringBuilder sb = new StringBuilder();
        while (i < n && Character.isDigit(formula.charAt(i))) {
            sb.append(formula.charAt(i++));
        }
        return Integer.valueOf(sb.toString());
    }

    public String parseAtom() {
        StringBuilder sb = new StringBuilder();
        sb.append(formula.charAt((i++)));
        while (i < n && Character.isLowerCase(formula.charAt(i))) {
            sb.append(formula.charAt(i++));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String formula = "K40(ON(SO3)2)2";
        Solution solution = new Solution();
        System.out.println(solution.countOfAtoms(formula));


    }
}
