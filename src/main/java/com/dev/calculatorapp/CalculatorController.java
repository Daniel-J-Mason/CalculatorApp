package com.dev.calculatorapp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorController {
    public TextArea inputTextArea;
    public Label historyLabel;
    
    public ToggleButton radianDegreeButton;
    public ToggleButton inverseButton;
    
    public Button clearEntryButton;
    
    public Button sineButton;
    public Button inverseSineButton;
    
    public Button cosineButton;
    public Button inverseCosineButton;
    
    public Button tangentButton;
    public Button inverseTangentButton;
    
    public Button squareRootButton;
    public Button squaredButton;
    
    public Button toThePowerOfButton;
    public Button factorialButton;
    
    
    public Button logButton;
    public Button tenToThePowerOfButton;
    
    public Button naturalLogButton;
    public Button eToThePowerOfButton;
    
    private final Calculator calculatorModel = new Calculator();
    
    public void initialize() {
        inputTextArea.setEditable(false);
        
        // Set inverse buttons invisible; Could likely write a class to group these or a custom Node
        inverseSineButton.setVisible(false);
        inverseSineButton.setDisable(true);
        
        inverseCosineButton.setVisible(false);
        inverseCosineButton.setDisable(true);
        
        inverseTangentButton.setVisible(false);
        inverseTangentButton.setDisable(true);
        
        squaredButton.setVisible(false);
        squaredButton.setDisable(true);
        
        factorialButton.setVisible(false);
        factorialButton.setDisable(true);
        
        tenToThePowerOfButton.setVisible(false);
        tenToThePowerOfButton.setDisable(true);
        
        eToThePowerOfButton.setVisible(false);
        eToThePowerOfButton.setDisable(true);
    }
    
    public void numberInput(ActionEvent actionEvent) {
        Button buttonInstance = (Button) actionEvent.getSource();
        inputTextArea.setText(inputTextArea.getText() + buttonInstance.getText());
    }
    
    public void operatorInput(ActionEvent actionEvent) {
        Button buttonInstance = (Button) actionEvent.getSource();
        if (inputTextArea.getText().equals("")) {
            inputTextArea.setText(historyLabel.getText() + " " + buttonInstance.getText() + " ");
        } else if (lastIsOperator()) {
            int lastCharacterIndex = inputTextArea.getText().length();
            String operatorRemoved = inputTextArea.getText().substring(0, lastCharacterIndex - 2);
            inputTextArea.setText(operatorRemoved + buttonInstance.getText() + " ");
        } else {
            inputTextArea.setText(inputTextArea.getText() + " " + buttonInstance.getText() + " ");
        }
    }
    
    public void parentheticOperator(ActionEvent actionEvent) {
        String userInput = inputTextArea.getText();
        Button buttonInstance = (Button) actionEvent.getSource();
        
        if (inputTextArea.getText().equals("")) {
            inputTextArea.setText(buttonInstance.getText() + "(" + historyLabel.getText() + ")");
        } else if (lastIsOperator()) {
            inputTextArea.setText(userInput + buttonInstance.getText() + "()");
        } else {
            inputTextArea.setText(buttonInstance.getText() + "(" + userInput + ")");
        }
    }
    
    public void exponentialOperator(ActionEvent actionEvent) {
        Button buttonInstance = (Button) actionEvent.getSource();
        String buttonText = buttonInstance.getText();
        
        switch (buttonText) {
            case "x^2":
                inputTextArea.setText("(" + inputTextArea.getText() + ") ^ 2");
                break;
            case "10^x":
                inputTextArea.setText("10 ^ (" + inputTextArea.getText() + ")");
                break;
            case "e^x":
                inputTextArea.setText("e ^ (" + inputTextArea.getText() + ")");
                break;
        }
    }
    
    public void factorial(ActionEvent actionEvent) {
        String userInput = inputTextArea.getText();
        
        if (inputTextArea.getText().equals("")) {
            inputTextArea.setText(historyLabel.getText() + "!");
        } else if (!lastIsOperator()) {
            inputTextArea.setText(userInput + "!");
        }
    }
    
    public void evaluate(ActionEvent actionEvent) {
        historyLabel.setText(calculatorModel.evaluate(inputTextArea.getText()));
        inputTextArea.setText("");
    }
    
    public void clearEntry(ActionEvent actionEvent) {
        if (!(inputTextArea.getText().equals(""))) {
            String current = inputTextArea.getText();
            if (inputTextArea.getText().substring(current.length() -1).equals(" ")){
                inputTextArea.setText(current.substring(0, current.length() - 2));
            } else {
                inputTextArea.setText(current.substring(0, current.length() - 1));
            }
        }
    }
    
    public void clearInputArea(ActionEvent actionEvent) {
        if (inputTextArea.getText().equals("")) {
            historyLabel.setText("");
        } else {
            inputTextArea.setText("");
        }
    }
    
    public void copyAnswerToInput(ActionEvent actionEvent) {
        inputTextArea.setText(inputTextArea.getText() + historyLabel.getText());
    }
    
    public void toggleRadianDegree(ActionEvent actionEvent) {
        if (radianDegreeButton.isSelected()) {
            radianDegreeButton.setText("Degrees");
            calculatorModel.setInRadians(false);
        } else {
            radianDegreeButton.setText("Radians");
            calculatorModel.setInRadians(true);
        }
    }
    
    public void toggleInverse(ActionEvent actionEvent) {
        invertButton(inverseButton.isSelected(), sineButton, inverseSineButton);
        invertButton(inverseButton.isSelected(), cosineButton, inverseCosineButton);
        invertButton(inverseButton.isSelected(), tangentButton, inverseTangentButton);
        invertButton(inverseButton.isSelected(), squareRootButton, squaredButton);
        invertButton(inverseButton.isSelected(), toThePowerOfButton, factorialButton);
        invertButton(inverseButton.isSelected(), logButton, tenToThePowerOfButton);
        invertButton(inverseButton.isSelected(), naturalLogButton, eToThePowerOfButton);
    }
    
    private void invertButton(boolean isInverted, Button defaultButton, Button alternativeButton) {
        defaultButton.setVisible(!isInverted);
        defaultButton.setDisable(isInverted);
        alternativeButton.setVisible(isInverted);
        alternativeButton.setDisable(!isInverted);
    }
    
    private boolean lastIsOperator() {
        //If last character is an operator
        Pattern pattern = Pattern.compile(".?[x+/\\-^] $");
        Matcher matcher = pattern.matcher(inputTextArea.getText());
        return matcher.find();
    }
    
}