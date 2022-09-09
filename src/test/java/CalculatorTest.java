import com.dev.calculatorapp.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Stream;

public class CalculatorTest {
    
    Calculator calculator = null;
    
    @BeforeEach
    public void initializeCalculator() {
        calculator = new Calculator();
    }
    
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("Arithmetic Operations")
    class arithmetic {
        
        @ValueSource
        private Stream<Arguments> inputPairs() {
            ArrayList<String> singleValues = new ArrayList<>(Arrays.asList("10", "1", "0", "0.1", "-.1", "-5", "100", "-.2345"));
            ArrayList<String> shuffledValues = (ArrayList<String>) singleValues.clone();
            Collections.shuffle(shuffledValues);
            
            //Return pairs are added as both Strings and Doubles for cleaner testing
            List<Arguments> returnPairs = new ArrayList<>();
            
            for (int i = 0; i < singleValues.size(); i++) {
                returnPairs.add(Arguments.of(singleValues.get(i), shuffledValues.get(i),
                        Double.parseDouble(singleValues.get(i)), Double.parseDouble(shuffledValues.get(i))));
            }
            
            return returnPairs.stream();
        }
        
        @ParameterizedTest(name = "{index}: addition of {0} and {1}")
        @MethodSource("inputPairs")
        @DisplayName("addition")
        public void addition(String firstValue, String secondValue, Double firstDouble, Double secondDouble) {
            Assertions.assertEquals(String.valueOf(firstDouble + secondDouble),
                    calculator.evaluate(firstValue + " + " + secondValue));
        }
        
        @ParameterizedTest(name = "{index}: subtraction of {0} and {1}")
        @MethodSource("inputPairs")
        @DisplayName("subtraction")
        public void subtraction(String firstValue, String secondValue, Double firstDouble, Double secondDouble) {
            Assertions.assertEquals(String.valueOf(firstDouble - secondDouble),
                    calculator.evaluate(firstValue + " - " + secondValue));
        }
        
        @ParameterizedTest(name = "{index}: multiplication of {0} and {1}")
        @MethodSource("inputPairs")
        @DisplayName("multiplication")
        public void multiplication(String firstValue, String secondValue, Double firstDouble, Double secondDouble) {
            Assertions.assertEquals(String.valueOf(firstDouble * secondDouble),
                    calculator.evaluate(firstValue + " x " + secondValue));
        }
        
        @ParameterizedTest(name = "{index}: division of {0} and {1}")
        @MethodSource("inputPairs")
        @DisplayName("division")
        public void division(String firstValue, String secondValue, Double firstDouble, Double secondDouble) {
            Assertions.assertEquals(String.valueOf(firstDouble / secondDouble),
                    calculator.evaluate(firstValue + " / " + secondValue));
        }
        
        @ParameterizedTest(name = "{index}: {0} to the power of {1}")
        @MethodSource("inputPairs")
        @DisplayName("exponent")
        public void exponent(String firstValue, String secondValue, Double firstDouble, Double secondDouble) {
            Assertions.assertEquals(String.valueOf(Math.pow(firstDouble, secondDouble)),
                    calculator.evaluate(firstValue + " ^ " + secondValue));
        }
        
        @Test
        @DisplayName("special characters")
        public void specialCharacters() {
            Assertions.assertEquals(String.valueOf(Math.PI), calculator.evaluate("π"));
            Assertions.assertEquals(String.valueOf(Math.E), calculator.evaluate("e"));
        }
        
        @Test
        @DisplayName("incorrect format test")
        public void formatErrors() {
            Assertions.assertEquals("Error!", calculator.evaluate("3 + + 4"));
        }
    }
    
    
    @Test
    public void complexProblems() {
        Assertions.assertEquals("25.5", calculator.evaluate("50 - 100 + 75.5"));
        Assertions.assertEquals("36.0", calculator.evaluate("3 x 5 + 21"));
        Assertions.assertEquals("18.0", calculator.evaluate("3 x ((6 + 6) / 2)"));
        Assertions.assertEquals("2.23606797749979", calculator.evaluate("√((((3 + 2)! / 2) / 60) + 4)"));
    }
    
    //
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("Trigonometric Functions")
    class TrigFunctions {
        
        @ValueSource
        private Stream<String> inputList() {
            return Stream.of("10", "1", "0", "0.1", "-.1", "-5", "100", "-.2345");
        }
        
        @ParameterizedTest(name = "{index}: sin({0})")
        @MethodSource(value = "inputList")
        @DisplayName("sin test")
        public void sin(String argument) {
            double value = Double.parseDouble(argument);
            calculator.setInRadians(true);
            Assertions.assertEquals(String.valueOf(Math.sin(value)), calculator.evaluate("sin(" + argument + ")"));
            calculator.setInRadians(false);
            Assertions.assertEquals(String.valueOf(Math.sin(Math.toRadians(value))), calculator.evaluate("sin(" + argument + ")"));
        }
        
        @ParameterizedTest(name = "{index}: cos({0})")
        @MethodSource(value = "inputList")
        @DisplayName("cos test")
        public void cos(String argument) {
            double value = Double.parseDouble(argument);
            calculator.setInRadians(true);
            Assertions.assertEquals(String.valueOf(Math.cos(value)), calculator.evaluate("cos(" + argument + ")"));
            calculator.setInRadians(false);
            Assertions.assertEquals(String.valueOf(Math.cos(Math.toRadians(value))), calculator.evaluate("cos(" + argument + ")"));
        }
        
        @ParameterizedTest(name = "{index}: tan({0})")
        @MethodSource(value = "inputList")
        @DisplayName("tan test")
        public void tan(String argument) {
            double value = Double.parseDouble(argument);
            calculator.setInRadians(true);
            Assertions.assertEquals(String.valueOf(Math.tan(value)), calculator.evaluate("tan(" + argument + ")"));
            calculator.setInRadians(false);
            Assertions.assertEquals(String.valueOf(Math.tan(Math.toRadians(value))), calculator.evaluate("tan(" + argument + ")"));
        }
        
        @ParameterizedTest(name = "{index}: asin({0})")
        @MethodSource(value = "inputList")
        @DisplayName("asin test")
        public void asin(String argument) {
            double value = Double.parseDouble(argument);
            calculator.setInRadians(true);
            Assertions.assertEquals(String.valueOf(Math.asin(value)), calculator.evaluate("asin(" + argument + ")"));
            calculator.setInRadians(false);
            Assertions.assertEquals(String.valueOf(Math.asin(Math.toRadians(value))), calculator.evaluate("asin(" + argument + ")"));
        }
        
        @ParameterizedTest(name = "{index}: acos({0})")
        @MethodSource(value = "inputList")
        @DisplayName("acos test")
        public void acos(String argument) {
            double value = Double.parseDouble(argument);
            calculator.setInRadians(true);
            Assertions.assertEquals(String.valueOf(Math.acos(value)), calculator.evaluate("acos(" + argument + ")"));
            calculator.setInRadians(false);
            Assertions.assertEquals(String.valueOf(Math.acos(Math.toRadians(value))), calculator.evaluate("acos(" + argument + ")"));
        }
        
        @ParameterizedTest(name = "{index}: atan({0})")
        @MethodSource(value = "inputList")
        @DisplayName("atan test")
        public void atan(String argument) {
            double value = Double.parseDouble(argument);
            calculator.setInRadians(true);
            Assertions.assertEquals(String.valueOf(Math.atan(value)), calculator.evaluate("atan(" + argument + ")"));
            calculator.setInRadians(false);
            Assertions.assertEquals(String.valueOf(Math.atan(Math.toRadians(value))), calculator.evaluate("atan(" + argument + ")"));
        }
        
        @ParameterizedTest(name = "{index}: log({0})")
        @MethodSource(value = "inputList")
        @DisplayName("log test")
        public void log(String argument) {
            double value = Double.parseDouble(argument);
            Assertions.assertEquals(String.valueOf(Math.log10(value)), calculator.evaluate("log(" + argument + ")"));
        }
    
        @ParameterizedTest(name = "{index}: ln({0})")
        @MethodSource(value = "inputList")
        @DisplayName("natural log test")
        public void naturalLog(String argument) {
            double value = Double.parseDouble(argument);
            Assertions.assertEquals(String.valueOf(Math.log(value)), calculator.evaluate("ln(" + argument + ")"));
        }
    }
}
