package com.dev.calculatorapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Calculator {
    
    private final HashMap<String, Integer> orderOfOperations = new HashMap<>();
    private final ArrayList<String> functions = new ArrayList<>();
    private Boolean inRadians;
    
    public Calculator() {
        setFunctions();
        setOrderOfOperations();
        inRadians = true;
    }
    
    public String evaluate(String equation) {
        ArrayList<String> postfixArray = transformToPostfix(equation);
        return reducePostfix(postfixArray);
    }
    
    // Shunting yard algorithm
    public ArrayList<String> transformToPostfix(String equation) throws IndexOutOfBoundsException {
        
        ArrayList<String> operationStack = new ArrayList<>();
        ArrayList<String> postFixEquation = new ArrayList<>();
        
        //TODO: Try catch null exception
        
        StringTokenizer tokens = new StringTokenizer(equation, "[ ()!]", true);
        while (tokens.hasMoreTokens()) {
            String currentToken = tokens.nextToken();
            if (isANumber(currentToken)) {
                postFixEquation.add(currentToken);
            } else if (isASpecialNumber(currentToken)) {
                postFixEquation.add(getSpecialNumberValue(currentToken));
            } else if (currentToken.equals("!")){ // Create separate method if multiple unary postfix functions
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
                
                if (!operationStack.isEmpty()) {
                    String modifiedLastOperator = operationStack.get(operationStack.size() - 1);
                    if (isAFunction(modifiedLastOperator)) {
                        postFixEquation.add(modifiedLastOperator);
                        operationStack.remove(modifiedLastOperator);
                    }
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
    
    private String reducePostfix(ArrayList<String> postfixArray){
        try {
            while (postfixArray.size() > 1) {
                for (int i = 0; i < postfixArray.size(); i++) {
                    if (isAFunction(postfixArray.get(i))) {
                        String operatorOne =  postfixArray.get(i - 1);
                        String function = postfixArray.get(i);
                    
                        String result = unaryOperation(operatorOne, function);
                    
                        postfixArray.remove(operatorOne);
                        postfixArray.remove(function);
                        postfixArray.add(i - 1, result);
                        i = 0;
                    } else if (orderOfOperations.containsKey(postfixArray.get(i))) {
                        String operatorOne = postfixArray.get(i - 2);
                        String operatorTwo = postfixArray.get(i - 1);
                        String operand = postfixArray.get(i);
                    
                        String result = binaryOperation(operatorOne, operatorTwo, operand);
                    
                        postfixArray.remove(operatorTwo);
                        postfixArray.remove(operatorOne);
                        postfixArray.remove(operand);
                        postfixArray.add(i - 2, result);
                        i = 0;
                    }
                }
            }
            //This reformats the number in case something like 0123 is evaluated with no functions/operators
            return String.valueOf(Double.parseDouble(postfixArray.get(0)));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return "Error!";
        }
    }
    
    private String binaryOperation(String x, String y, String operator) {
        double first = Double.parseDouble(x);
        double second = Double.parseDouble(y);
    
        return switch (operator) {
            case "+" -> String.valueOf(first + second);
            case "-" -> String.valueOf(first - second);
            case "/" -> String.valueOf(first / second);
            case "x" -> String.valueOf(first * second);
            case "^" -> String.valueOf(Math.pow(first, second));
            default -> null;
        };
    }
    
    private String unaryOperation(String x, String function) {
        double inputValue = Double.parseDouble(x);
        
        String result = null;
        
        // Non-trig functions
        switch (function) {
            case "!" -> result = factorial(inputValue);
            case "√" -> result = String.valueOf(Math.sqrt(inputValue)); //Square Root
            case "log" -> result = String.valueOf(Math.log10(inputValue));
            case "ln" -> result = String.valueOf(Math.log(inputValue));
        }
        
        //Account for radians
        if (!inRadians) {
            inputValue = Math.toRadians(inputValue);
        }
        result = switch (function) {
            case "sin" -> String.valueOf(Math.sin(inputValue));
            case "cos" -> String.valueOf(Math.cos(inputValue));
            case "tan" -> String.valueOf(Math.tan(inputValue));
            case "asin" -> String.valueOf(Math.asin(inputValue));
            case "acos" -> String.valueOf(Math.acos(inputValue));
            case "atan" -> String.valueOf(Math.atan(inputValue));
            default -> result;
        };
        return result;
    }
    
    private void setFunctions(){
        functions.add("sin");
        functions.add("tan");
        functions.add("cos");
        functions.add("asin");
        functions.add("acos");
        functions.add("atan");
        functions.add("log");
        functions.add("ln");
        functions.add("!");
        functions.add("√");
    }
    
    private void setOrderOfOperations(){
        orderOfOperations.put("+", 0);
        orderOfOperations.put("-", 0);
        orderOfOperations.put("x", 1);
        orderOfOperations.put("/", 1);
        orderOfOperations.put("^", 2);
        functions.forEach(function -> orderOfOperations.put(function, 0));
    }
    
    private String factorial(double number) {
        double reduced = IntStream.rangeClosed(2, (int) number).reduce(1, (x, y) -> x * y);
        return String.valueOf(reduced);
    }
    
    private boolean isANumber(String equationToken) {
        Pattern simpleNumber = Pattern.compile("-?\\d.?\\d*|-?\\d*.?\\d+");
        return simpleNumber.matcher(equationToken).matches();
    }
    
    private boolean isASpecialNumber(String equationToken) {
        return equationToken.equals("e") ||
                equationToken.equals("π");
    }
    
    private String getSpecialNumberValue(String equationToken) {
        return switch (equationToken) {
            case "π" -> String.valueOf(Math.PI);
            case "e" -> String.valueOf(Math.E);
            default -> null;
        };
    }
    
    public void setInRadians(Boolean inRadians) {
        this.inRadians = inRadians;
    }
    
    private boolean isAFunction(String equationToken) {
        return functions.contains(equationToken);
    }
}
