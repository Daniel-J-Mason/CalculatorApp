package com.dev.calculatorapp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

public class CalculatorController {
    public TextArea answerTextArea;
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
    
    public Button factorialButton;
    public Button toThePowerOfButton;
    
    public Button logButton;
    public Button tenToThePowerOfButton;
    
    public Button naturalLogButton;
    public Button eToThePowerOf;

    
    public void initialize(){
        answerTextArea.setEditable(false);
        
        // Set inverse buttons invisible; Could likely write a class to group these
        inverseSineButton.setVisible(false);
        inverseSineButton.setDisable(true);
        
        inverseCosineButton.setVisible(false);
        inverseCosineButton.setDisable(true);
        
        inverseTangentButton.setVisible(false);
        inverseTangentButton.setDisable(true);
        
        squaredButton.setVisible(false);
        squaredButton.setDisable(true);
        
        toThePowerOfButton.setVisible(false);
        toThePowerOfButton.setDisable(true);
        
        tenToThePowerOfButton.setVisible(false);
        tenToThePowerOfButton.setDisable(true);
        
        eToThePowerOf.setVisible(false);
        eToThePowerOf.setDisable(true);
    }
    
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
        calculatorModel.evaluate(answerTextArea.getText());
        historyLabel.setText(String.valueOf(calculatorModel.getTotal()));
        answerTextArea.setText("");
    }
    
    public void clearEntry(ActionEvent actionEvent) {
        if (!(answerTextArea.getText().equals(""))){
            String current = answerTextArea.getText();
            answerTextArea.setText(current.substring(0, current.length() - 1));
        }
    }
    
    public void toggleRadianDegree(ActionEvent actionEvent) {
        if (radianDegreeButton.isSelected()) {
            radianDegreeButton.setText("Degrees");
        } else {
            radianDegreeButton.setText("Radians");
        }
    }
    
    public void toggleInverse(ActionEvent actionEvent) {
        invertButton(inverseButton.isSelected(), sineButton, inverseSineButton);
        invertButton(inverseButton.isSelected(), cosineButton, inverseCosineButton);
        invertButton(inverseButton.isSelected(), tangentButton, inverseTangentButton);
        invertButton(inverseButton.isSelected(), squareRootButton, squaredButton);
        invertButton(inverseButton.isSelected(), factorialButton, toThePowerOfButton);
        invertButton(inverseButton.isSelected(), logButton, tenToThePowerOfButton);
        invertButton(inverseButton.isSelected(), naturalLogButton, eToThePowerOf);
    }
    
    private void invertButton(boolean isInverted, Button defaultButton, Button alternativeButton) {
        defaultButton.setVisible(!isInverted);
        defaultButton.setDisable(isInverted);
        alternativeButton.setVisible(isInverted);
        alternativeButton.setDisable(!isInverted);
    }
    
    public void parentheticOperator(ActionEvent actionEvent) {
        String userInput = answerTextArea.getText();
        Button buttonInstance = (Button) actionEvent.getSource();
        
        if (answerTextArea.getText().equals("")){
            answerTextArea.setText(buttonInstance.getText() + "(" + historyLabel.getText() + ")");
        } else {
            answerTextArea.setText(buttonInstance.getText() + "(" + userInput + ")");
        }
    }
    
    public void exponentialOperator(ActionEvent actionEvent) {
    
    }
}