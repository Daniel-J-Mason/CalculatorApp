package com.dev.calculatorapp;

public class Calculator {
    private float currentTotal;
    private float previousTotal;

    public Calculator(){
        currentTotal = 0;
        previousTotal = 0;
    }

    private void update(){
        previousTotal = currentTotal;
    }

    public float add(float x){
        currentTotal = previousTotal + x;
        update();
        return currentTotal;
    }

    public float subtract(float x) {
        currentTotal = previousTotal - x;
        update();
        return currentTotal;
    }

    public float getCurrentTotal() {
        return currentTotal;
    }

    public float getPreviousTotal() {
        return previousTotal;
    }
}
