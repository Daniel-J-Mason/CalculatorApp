package com.dev.calculatorapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.Formatter;

public class HelloController {
    public Button addButton;
    public TextArea answerTextArea;
    public Button equalButton;
    public Label historyLabel;
    public Button zeroButton;
    public Button oneButton;
    public Button twoButton;
    public Button threeButton;
    public Button fourButton;
    public Button fiveButton;
    public Button sixButton;
    public Button sevenButton;
    public Button eightButton;
    public Button nineButton;
    
    private final Calculator calculatorModel = new Calculator();
    
    public void numberInput(ActionEvent actionEvent) {
        Button buttonInstance = (Button) actionEvent.getSource();
        answerTextArea.setText(answerTextArea.getText() + buttonInstance.getText());
    }
    
    public void operatorInput(ActionEvent actionEvent) {
        Button buttonInstance = (Button) actionEvent.getSource();
        if (answerTextArea.getText().equals("")){
            answerTextArea.setText(historyLabel.getText() + " " + buttonInstance.getText() + " ");
        } else {
            answerTextArea.setText(answerTextArea.getText() + " " + buttonInstance.getText() + " ");
        }
    }
    
    public void evaluate(ActionEvent actionEvent) {
        String[] inputs = answerTextArea.getText().split(" ");
        float firstInput = Float.parseFloat(inputs[0]);
        float secondInput = Float.parseFloat(inputs[2]);
        String operator = inputs[1];
        calculatorModel.calculate(firstInput, secondInput, operator);
        historyLabel.setText(String.valueOf(calculatorModel.getTotal()));
        answerTextArea.setText("");
    }
}