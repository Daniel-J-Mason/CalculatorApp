package com.dev.calculatorapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private float total;

    public Calculator(){
        total = 0;
    }
    
    public void calculateStringPattern(String input){
        Pattern binaryOperation = Pattern.compile("(\\d+.?\\d*) ?([+\\-/x]) ?(\\d+.?\\d*)");
        Pattern unaryOperation = Pattern.compile("([+\\-/x]) ?(\\d+.?\\d*)");
        Pattern simpleNumber = Pattern.compile("\\d+.?\\d*");
        
        Matcher binaryMatch = binaryOperation.matcher(input);
        Matcher unaryMatch = unaryOperation.matcher(input);
        Matcher simpleMatch = simpleNumber.matcher(input);
        
        if (binaryMatch.matches()) {
            for (int i = 0; i < 5; i++) {
                System.out.println("Binary Group " + i + " " + binaryMatch.group(i));
            }
        } else if (unaryMatch.matches()) {
            for (int i = 0; i < 5; i++) {
                System.out.println("Unary Group " + i + " " + unaryMatch.group(i));
            }
        } else if (simpleMatch.matches()) {
            for (int i = 0; i < 5; i++) {
                System.out.println("Number Group " + i + " " + simpleMatch.group(i));
            }
        }
    }
    
    public void calculate(float x, float y, String operator){
        switch (operator) {
            case "+":
                total = x + y;
                break;
            case "-":
                total = x - y;
                break;
            case "/":
                total = x / y;
                break;
            case "x":
                total = x * y;
                break;
        }
    }
    
    public void calculate(float x, String operator){
        switch (operator) {
            case "+":
                total += x;
                break;
            case "-":
                total -= x;
                break;
            case "/":
                total /= x;
                break;
            case "x":
                total *= x;
                break;
        }
    }

    public float getTotal() {
        return total;
    }
    
    public void setTotal(float total) {
        this.total = total;
    }
    
}
