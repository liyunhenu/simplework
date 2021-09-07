package com.vector;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {


    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        int[] positon = new int[10000];//一万个号码牌
        Set<String> shaghuSet = new HashSet<>();
        while (cin.hasNext()) {
            String[] tmp = cin.nextLine().trim().split(" ");
            if (tmp[0].equals("-1")) {
                break;
            }
            if (tmp.length == 3) {
                String shanghu = tmp[0];
                String action = tmp[1];//操作
                String haomaNum = tmp[2];//二维码号牌
                String type = shanghu.substring(0, 1);//商户类型 S商户 U用户
                String shagnhuNum = shanghu.substring(1);//商户号码
                int num = Integer.valueOf(shagnhuNum);//操作商户号码
                int positionNum = Integer.valueOf(haomaNum.substring(2));//号码位置
                if (type.equals("S")) {
                    if (action.equals("bind")) {
                        if (shaghuSet.contains(shanghu)) {
                            System.out.println(shanghu + " " + action + " " + "fail");
                            continue;
                        }
                        if (positon[positionNum - 1] == 0) {
                            positon[positionNum - 1] = num;
                            shaghuSet.add(shanghu);//防止同一个商户放多个位置
                            System.out.println(shanghu + " " + action + " " + "success");
                        } else {
                            System.out.println(shanghu + " " + action + " " + "fail");
                        }
                    } else if (action.equals("unbind")) {
                        if (positon[positionNum - 1] == 0) {
                            System.out.println(shanghu + " " + action + " " + "fail");
                        } else {
                            if (positon[positionNum - 1] == num) {
                                positon[positionNum - 1] = 0;//解绑
                                shaghuSet.remove(shanghu);
                                System.out.println(shanghu + " " + action + " " + "success");
                            } else {
                                System.out.println(shanghu + " " + action + " " + "fail");
                            }
                        }
                    } else {
                        System.out.println("unknown command");
                    }
                } else if (type.equals("U")) {
                    if ("scan".equals(action)) {
                        if (positon[positionNum - 1] == 0) {
                            System.out.println(shanghu + " " + action + " " + "fail");
                        } else {
                            System.out.println(shanghu + " " + "pay to" + " " + "S" + positon[positionNum - 1]);
                        }
                    } else {
                        System.out.println("unknown command");
                    }

                }

            } else {
                System.out.println("unknown command");
            }

        }

    }

}
