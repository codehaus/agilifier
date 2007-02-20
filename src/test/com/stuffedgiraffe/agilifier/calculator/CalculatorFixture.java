package com.stuffedgiraffe.agilifier.calculator;

import fit.ColumnFixture;

public class CalculatorFixture extends ColumnFixture {

    public String value1 = "";
    public String operator = "";
    public String value2 = "";

    public String result() {
        long value1 = Integer.parseInt(this.value1);
        long value2 = Integer.parseInt(this.value2);
        if (operator.equals("+")) {
            long result = value1 + value2;
            if ((result > Integer.MAX_VALUE) || (result < Integer.MIN_VALUE)) {
                return "OVERFLOW";
            }
            return String.valueOf(result);
        }
        return "Unknown operator: " + operator;
    }

}
