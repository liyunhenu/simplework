package com.vector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * 费率试算
 */
public class Question1 {


    public static void main(String[] args) {
        Scanner si = new Scanner(System.in);
        String input1 = si.nextLine();
        String[] inputArray = input1.trim().split(" ");
        BigDecimal loanMoney = new BigDecimal(inputArray[0]);
        BigDecimal benjin=loanMoney;
        BigDecimal loanRate = new BigDecimal(inputArray[1]).divide(new BigDecimal(360),2, RoundingMode.HALF_UP);
        BigDecimal extendRate = new BigDecimal(inputArray[2]).divide(new BigDecimal(360),2, RoundingMode.HALF_UP);
        BigDecimal overRate = new BigDecimal(inputArray[3]).divide(new BigDecimal(360),2, RoundingMode.HALF_UP);
        int x = Integer.valueOf(inputArray[4]);
        int y = Integer.valueOf(inputArray[5]);
        BigDecimal interest = new BigDecimal(0.0);
        while (si.hasNext()) {
            int day = Integer.valueOf(si.next());
            if (day < x || day == x) {
                //贷款利率.divide(new BigDecimal(360))
                for (int i = 0; i < day; i++) {
                    loanMoney = loanMoney.multiply(loanRate.add(new BigDecimal(1)));
                }
                interest=loanMoney.subtract(benjin);
            } else if (day < y || day == y) {
                //展期利率
                for (int i = 0; i < x; i++) {
                    loanMoney = loanMoney.multiply(loanRate.add(new BigDecimal(1)));
                }
                for (int i = x; i <day ; i++) {
                    loanMoney = loanMoney.multiply(extendRate.add(new BigDecimal(1)));
                }
                interest=loanMoney.subtract(benjin);

            } else {
                //逾期利率
                for (int i = 0; i < x; i++) {
                    loanMoney = loanMoney.multiply(loanRate.add(new BigDecimal(1)));
                }
                for (int i = x; i <y ; i++) {
                    loanMoney = loanMoney.multiply(extendRate.add(new BigDecimal(1)));
                }
                for (int i = y; i <day ; i++) {
                    loanMoney = loanMoney.multiply(overRate.add(new BigDecimal(1)));
                }
                interest=loanMoney.subtract(benjin);
            }
            System.out.println(interest);

        }
    }
}
