package com.dev.calculatorapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Calculator {
    
    private final HashMap<String, Integer> orderOfOperations = new HashMap<>();
    private final ArrayList<String> functions = new ArrayList<>();
    
    private float total;
    
    private Boolean inRadians;
    
    public Calculator() {
        orderOfOperations.put("+", 0);
        orderOfOperations.put("-", 0);
        orderOfOperations.put("x", 1);
        orderOfOperations.put("/", 1);
        orderOfOperations.put("^", 2);
        
        functions.add("sin");
        functions.add("tan");
        functions.add("cos");
        functions.add("√");
        
        for (String function : functions) {
            orderOfOperations.put(function, 0);
        }
        
        inRadians = true;
        
        total = 0;
    }
    
    public String evaluate(String equation) {
        ArrayList<String> postfixArray = createPostfixArray(equation);
        
        try {
            while (postfixArray.size() > 1) {
                for (int i = 0; i < postfixArray.size(); i++) {
                    if (isAFunction(postfixArray.get(i))) {
                        String operatorOne = postfixArray.get(i - 1);
                        String function = postfixArray.get(i);
                        
                        String result = calculate(operatorOne, function);
                        
                        postfixArray.remove(operatorOne);
                        postfixArray.remove(function);
                        postfixArray.add(i - 1, result);
                        i = 0;
                    } else if (orderOfOperations.containsKey(postfixArray.get(i))) {
                        String operatorOne = postfixArray.get(i - 2);
                        String operatorTwo = postfixArray.get(i - 1);
                        String operand = postfixArray.get(i);
    
                        System.out.println(operatorOne + operatorTwo + operand);
                        String result = calculate(operatorOne, operatorTwo, operand);
                        
                        postfixArray.remove(operatorTwo);
                        postfixArray.remove(operatorOne);
                        postfixArray.remove(operand);
                        postfixArray.add(i - 2, result);
                        i = 0;
                    }
                }
            }
            return postfixArray.get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return "Error!";
        }
    }
    
    
    // Shunting yard algorithm
    public ArrayList<String> createPostfixArray(String equation) throws IndexOutOfBoundsException {
        
        ArrayList<String> operationStack = new ArrayList<>();
        ArrayList<String> postFixEquation = new ArrayList<>();
        
        //TODO: Try catch null exception
        
        StringTokenizer tokens = new StringTokenizer(equation, "[ ()]", true);
        while (tokens.hasMoreTokens()) {
            String currentToken = tokens.nextToken();
            if (isANumber(currentToken)) {
                postFixEquation.add(currentToken);
            } else if (isAFunction(currentToken)) {
                operationStack.add(currentToken);
            } else if (orderOfOperations.containsKey(currentToken)) {
                if (!operationStack.isEmpty()) {
                    String lastOperator = operationStack.get(operationStack.size() - 1);
                    
                    while (!operationStack.isEmpty()
                            && !lastOperator.equals("(")
                            && (orderOfOperations.get(lastOperator) >= orderOfOperations.get(currentToken))) {
                        lastOperator = operationStack.get(operationStack.size() - 1);
                        postFixEquation.add(lastOperator);
                        operationStack.remove(lastOperator);
                    }
                }
                
                operationStack.add(currentToken);
            } else if (currentToken.equals("(")) {
                operationStack.add(currentToken);
            } else if (currentToken.equals(")")) {
                String lastOperator = operationStack.get(operationStack.size() - 1);
                
                
                while (!lastOperator.equals("(")) {
                    postFixEquation.add(lastOperator);
                    operationStack.remove(lastOperator);
                    lastOperator = operationStack.get(operationStack.size() - 1);
                }
                
                operationStack.remove("(");
                operationStack.remove(")");
                
                
                //TODO: replace last operator
                String modifiedLastOperator = operationStack.get(operationStack.size() - 1);
                if (isAFunction(modifiedLastOperator)) {
                    postFixEquation.add(modifiedLastOperator);
                    operationStack.remove(modifiedLastOperator);
                }
            }
        }
        
        while (!operationStack.isEmpty()) {
            String lastOperator = operationStack.get(operationStack.size() - 1);
            postFixEquation.add(lastOperator);
            operationStack.remove(lastOperator);
        }
        
        return postFixEquation;
    }
    
    public String calculate(String x, String y, String operator) {
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
            case "^":
                result = String.valueOf(Math.pow(first, second));
        }
        
        return result;
    }
    
    public String calculate(String x, String function) {
        double first = Double.parseDouble(x);
        
        String result = null;
        
        // Non-trig functions
        switch (function) {
            case "!":
                float factorialSum = 1;
                for (int i = (int) first; i > 1; i--) {
                    factorialSum *= i;
                }
                result = String.valueOf(factorialSum);
                break;
            case "√": //Square Root
                result = String.valueOf(Math.sqrt(first));
                break;
        }
        
        //Account for radians
        if (!inRadians) {
            first = Math.toRadians(first);
        }
        switch (function) {
            case "sin":
                result = String.valueOf(Math.sin(first));
                break;
            case "cos":
                result = String.valueOf(Math.cos(first));
                break;
            case "tan":
                result = String.valueOf(Math.tan(first));
                break;
        }
        return result;
    }
    
    private boolean isANumber(String equationToken) {
        Pattern simpleNumber = Pattern.compile("-?\\d+.?\\d*");
        return simpleNumber.matcher(equationToken).matches();
    }
    
    private boolean isAFunction(String equationToken) {
        return functions.contains(equationToken);
    }
    
    public void setInRadians(Boolean inRadians) {
        this.inRadians = inRadians;
    }
    
    public float getTotal() {
        return total;
    }
    
    public void setTotal(float total) {
        this.total = total;
    }
    
}
