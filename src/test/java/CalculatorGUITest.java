import com.dev.calculatorapp.Calculator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(ApplicationExtension.class)
public class CalculatorGUITest {
    
    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/dev/calculatorapp/calculator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
    
    @ParameterizedTest
    @MethodSource(value = "trigonometryButtons")
    void degreesButton(String buttonId, String outputText, FxRobot robot) {
        Calculator calculator = new Calculator();
        ArrayList<Boolean> toggle = new ArrayList<>(Arrays.asList(false, true));
        
        for (boolean element : toggle) {
            calculator.setInRadians(element);
            robot.clickOn("#radianDegreeButton");
            robot.clickOn("7");
            robot.clickOn("#" + buttonId);
            robot.clickOn("=");
            String evaluation = calculator.evaluate(outputText + "(" + 7 + ")");
            Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText(evaluation);
            robot.clickOn("C");
        }
        
    }
    
    @ParameterizedTest
    @MethodSource(value = "trigonometryButtons")
    void trigonometryButtonTests(String buttonId, String outputText, FxRobot robot) {
        Calculator calculator = new Calculator();
        robot.clickOn("#" + buttonId);
        Assertions.assertThat(robot.lookup("#inputTextArea").queryAs(TextArea.class)).hasText(outputText + "()");
        
        robot
                .clickOn("C")
                .clickOn("3")
                .clickOn("#" + buttonId).clickOn("=");
        
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText(calculator.evaluate(outputText + "(" + "3" + ")"));
    }
    
    @ParameterizedTest
    @MethodSource(value = "inverseTrigonometryButtons")
    void inverseTrigonometryButtonTests(String buttonId, String outputText, FxRobot robot) {
        Calculator calculator = new Calculator();
        
        robot
                .clickOn("Inv")
                .clickOn("#" + buttonId);
        
        Assertions.assertThat(robot.lookup("#inputTextArea").queryAs(TextArea.class)).hasText(outputText + "()");
        
        robot
                .clickOn("C")
                .clickOn("3")
                .clickOn("#" + buttonId)
                .clickOn("=");
        
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText(calculator.evaluate(outputText + "(" + "3" + ")"));
    }
    
    @Test
    void numericalInput(FxRobot robot) {
        for (int i = 0; i <= 8; i++) {
            robot.clickOn(String.valueOf(i));
        }
        
        robot
                .clickOn("#decimalTest")
                .clickOn("9")
                .clickOn("=");
        
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText("1.23456789E7");
    }
    
    //Complex operations ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Test
    void exponentialClear(FxRobot robot) {
        robot
                .clickOn("5")
                .clickOn("x")
                .clickOn("+")
                .clickOn("Inv")
                .clickOn("x^2")
                .clickOn("CE")
                .clickOn("CE")
                .clickOn("CE")
                .clickOn("2")
                .clickOn(")")
                .clickOn("Inv")
                .clickOn("^")
                .clickOn("3")
                .clickOn("=");
        
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText("343.0");
    }
    
    @Test
    void operatorAndFunction(FxRobot robot) {
        robot.clickOn("3")
                .clickOn("+")
                .clickOn("log")
                .clickOn("CE")
                .clickOn("1")
                .clickOn("0")
                .clickOn(")")
                .clickOn("=");
        
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText("4.0");
    }
    
    @Test
    void specialCharacters(FxRobot robot) {
        Calculator calculator = new Calculator();
        robot
                .clickOn("e")
                .clickOn("x")
                .clickOn("π")
                .clickOn("/")
                .clickOn("1")
                .clickOn("Inv")
                .clickOn("x!")
                .clickOn("=");
        
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText(calculator.evaluate("e x π"));
    }
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    @Test
    void emptyInput(FxRobot robot){
        robot
                .clickOn("3")
                .clickOn("=")
                .clickOn("+")
                .clickOn("3")
                .clickOn("=");
        
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText("6.0");
    
        robot
                .clickOn("C")
                .clickOn("5")
                .clickOn("=")
                .clickOn("Inv")
                .clickOn("x!")
                .clickOn("=");
    
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText("120.0");
    }
    
    @Test
    void ansButton(FxRobot robot){
        Calculator calculator = new Calculator();
        robot
                .clickOn("3")
                .clickOn("=")
                .clickOn("Ans")
                .clickOn("Inv")
                .clickOn("10^x")
                .clickOn("=");
        
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText("1000.0");
    
        robot
                .clickOn("2")
                .clickOn("=")
                .clickOn("Ans")
                .clickOn("e^x")
                .clickOn("=");
    
        Assertions.assertThat(robot.lookup("#historyLabel").queryAs(Label.class)).hasText(calculator.evaluate("e ^ 2"));
    }
    
    //TODO: reduce to arraylist, can query by name
    @ValueSource
    private static Stream<Arguments> trigonometryButtons() {
        List<Arguments> outputPairs = new ArrayList<>();
        HashMap<String, String> buttonToInput = new HashMap<>();
        {
            buttonToInput.put("cosineButton", "cos");
            buttonToInput.put("sineButton", "sin");
            buttonToInput.put("tangentButton", "tan");
        }
        
        buttonToInput.
                keySet().
                forEach(element -> outputPairs.add(Arguments.of(element, buttonToInput.get(element))));
        
        return outputPairs.stream();
    }
    
    @ValueSource
    private static Stream<Arguments> inverseTrigonometryButtons() {
        List<Arguments> outputPairs = new ArrayList<>();
        HashMap<String, String> buttonToInput = new HashMap<>();
        {
            buttonToInput.put("inverseCosineButton", "acos");
            buttonToInput.put("inverseSineButton", "asin");
            buttonToInput.put("inverseTangentButton", "atan");
        }
        
        buttonToInput.keySet()
                .forEach(element -> outputPairs.add(Arguments.of(element, buttonToInput.get(element))));
        
        return outputPairs.stream();
    }
}
