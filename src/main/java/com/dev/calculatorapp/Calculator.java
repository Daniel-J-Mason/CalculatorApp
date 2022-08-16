package com.dev.calculatorapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    
    private final HashMap<String, Integer> orderOfOperations = new HashMap<>();
    
    private float total;

    public Calculator(){
        orderOfOperations.put("+", 0);
        orderOfOperations.put("-", 0);
        orderOfOperations.put("x", 1);
        orderOfOperations.put("/", 1);
        orderOfOperations.put("^", 2);
        total = 0;
    }
    
    public String evaluate(String equation) {
        ArrayList<String> postfixArray = createPostfixArray(equation);
        while (postfixArray.size() > 1) {
            System.out.println(postfixArray);
            for (int i = 0; i < postfixArray.size(); i++) {
                if (orderOfOperations.containsKey(postfixArray.get(i))){
                    String operatorOne = postfixArray.get(i - 2);
                    String operatorTwo = postfixArray.get(i - 1);
                    String operand = postfixArray.get(i);
                    
                    String result = calculate(operatorOne, operatorTwo, operand);
                    
                    postfixArray.remove(operatorTwo);
                    postfixArray.remove(operatorOne);
                    postfixArray.remove(operand);
                    postfixArray.add(i - 2, result);
                    break;
                }
            }
        }
        
        return postfixArray.get(0);
    }
    
    public ArrayList<String> createPostfixArray(String equation){
    
        ArrayList<String> operationStack = new ArrayList<>();
        ArrayList<String> postFixEquation = new ArrayList<>();
    
        StringTokenizer tokens = new StringTokenizer(equation, "[+-x/^]", true);
        while (tokens.hasMoreTokens()) {
            String currentToken = tokens.nextToken();
            if (isANumber(currentToken)){
                postFixEquation.add(currentToken);
            } else if (orderOfOperations.containsKey(currentToken)){
                if (!operationStack.isEmpty()) {
                    String lastOperator = operationStack.get(operationStack.size() - 1);
    
                    while (!operationStack.isEmpty()
                            && (orderOfOperations.get(lastOperator) >= orderOfOperations.get(currentToken))
                            && !lastOperator.equals("(")) {
                        lastOperator = operationStack.get(operationStack.size() - 1);
                        postFixEquation.add(lastOperator);
                        operationStack.remove(lastOperator);
                    }
                }
                
                operationStack.add(currentToken);
            } else if (currentToken.equals("(")) {
                operationStack.add(currentToken);
            } else if (currentToken.equals(")")){
                String lastOperator = operationStack.get(operationStack.size() - 1);
                
                while (!lastOperator.equals("(")) {
                    postFixEquation.add(lastOperator);
                    operationStack.remove(lastOperator);
                    lastOperator = operationStack.get(operationStack.size() - 1);
                }
                
                operationStack.remove("(");
                operationStack.remove(")");
            }
        }
    
        while (!operationStack.isEmpty()) {
            String lastOperator = operationStack.get(operationStack.size() - 1);
            postFixEquation.add(lastOperator);
            operationStack.remove(lastOperator);
        }
        
        return postFixEquation;
    }
    
    public String calculate(String x, String y, String operator){
        double first = Double.parseDouble(x);
        double second = Double.parseDouble(y);
        
        String result = null;
        
        switch (operator) {
            case "+":
                result = String.valueOf(first + second);
                break;
            case "-":
                result = String.valueOf(first - second);
                break;
            case "/":
                result = String.valueOf(first / second);
                break;
            case "x":
                result = String.valueOf(first * second);
                break;
        }
        
        return result;
    }
    
    public void calculate(float x, String operator){
        switch (operator) {
            case "!":
                float factorialSum = 1;
                for (int i = (int) total; i > 1; i--){
                    factorialSum *= i;
                }
                total = factorialSum;
                break;
            case "√": //Square Root
                total = (float) Math.sqrt(total);
                break;
            case "":
                total /= x;
                break;
            case "x":
                total *= x;
                break;
        }
    }
    
    private boolean isANumber(String equationToken) {
        Pattern simpleNumber = Pattern.compile("\\d+.?\\d*");
        return simpleNumber.matcher(equationToken).matches();
    }

    public float getTotal() {
        return total;
    }
    
    public void setTotal(float total) {
        this.total = total;
    }
    
}
