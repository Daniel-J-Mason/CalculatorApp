import com.dev.calculatorapp.Calculator;
import org.junit.jupiter.api.*;

import java.util.*;

public class CalculatorTest {
    
    Calculator calculator = null;
    private final static ArrayList<String> inputValues =
            new ArrayList<>(Arrays.asList("10", "1", "0", "0.1", "-.1", "-5", "100", "-.2345"));
    
    @BeforeEach
    public void initializeCalculator() {
        calculator = new Calculator();
    }
    
    @Nested
    @DisplayName("Arithmetic Operations")
    class arithmetic {
        
        @Test
        public void addition() {
            for (String element : inputPairs().keySet()) {
                String firstValue = element;
                String secondValue = inputPairs().get(element);
                
                Double firstDouble = Double.parseDouble(firstValue);
                Double secondDouble = Double.parseDouble(secondValue);
                
                Assertions.assertEquals(String.valueOf(firstDouble + secondDouble),
                        calculator.evaluate(firstValue + " + " + secondValue));
            }
        }
        
        @Test
        public void subtraction() {
            for (String element : inputPairs().keySet()) {
                String firstValue = element;
                String secondValue = inputPairs().get(element);
                
                Double firstDouble = Double.parseDouble(firstValue);
                Double secondDouble = Double.parseDouble(secondValue);
                
                Assertions.assertEquals(String.valueOf(firstDouble - secondDouble),
                        calculator.evaluate(firstValue + " - " + secondValue));
            }
        }
        
        @Test
        public void multiplication() {
            for (String element : inputPairs().keySet()) {
                String firstValue = element;
                String secondValue = inputPairs().get(element);
                
                Double firstDouble = Double.parseDouble(firstValue);
                Double secondDouble = Double.parseDouble(secondValue);
                
                Assertions.assertEquals(String.valueOf(firstDouble * secondDouble),
                        calculator.evaluate(firstValue + " x " + secondValue));
            }
        }
        
        @Test
        public void division() {
            for (String element : inputPairs().keySet()) {
                String firstValue = element;
                String secondValue = inputPairs().get(element);
                
                Double firstDouble = Double.parseDouble(firstValue);
                Double secondDouble = Double.parseDouble(secondValue);
                
                Assertions.assertEquals(String.valueOf(firstDouble / secondDouble),
                        calculator.evaluate(firstValue + " / " + secondValue));
            }
        }
    }
    
    @Test
    public void complexProblems() {
        Assertions.assertEquals("25.5", calculator.evaluate("50 - 100 + 75.5"));
        Assertions.assertEquals("36.0", calculator.evaluate("3 x 5 + 21"));
        Assertions.assertEquals("18.0", calculator.evaluate("3 x ((6 + 6) / 2)"));
        Assertions.assertEquals("2.23606797749979", calculator.evaluate("√((((3 + 2)! / 2) / 60) + 4)"));
    }
    
    @Nested
    @DisplayName("Trigonometric Operations")
    class trigonometry {
        
        @Test
        public void sin() {
            List<Double> inputValuesDoubles = new ArrayList<>();
            for (String element: inputValues) {
                double doubleValue = Double.parseDouble(element);
                System.out.println(doubleValue + " " + element);
                calculator.setInRadians(true);
                Assertions.assertEquals(String.valueOf(Math.sin(doubleValue)), calculator.evaluate("sin(" + element +")"));
    
                calculator.setInRadians(false);
                double radianValue = Math.toRadians(doubleValue);
                Assertions.assertEquals(String.valueOf(Math.sin(radianValue)), calculator.evaluate("sin("+ element + ")"));
            }
        }
    
        @Test
        public void asin() {
            for (String element: inputValues) {
                double doubleValue = Double.parseDouble(element);
                System.out.println(doubleValue + " " + element);
                calculator.setInRadians(true);
                Assertions.assertEquals(String.valueOf(Math.asin(doubleValue)), calculator.evaluate("asin(" + element +")"));
            
                calculator.setInRadians(false);
                double radianValue = Math.toRadians(doubleValue);
                Assertions.assertEquals(String.valueOf(Math.asin(radianValue)), calculator.evaluate("asin("+ element + ")"));
            }
        }
    
        @Test
        public void cos() {
            for (String element: inputValues) {
                double doubleValue = Double.parseDouble(element);
                System.out.println(doubleValue + " " + element);
                calculator.setInRadians(true);
                Assertions.assertEquals(String.valueOf(Math.cos(doubleValue)), calculator.evaluate("cos(" + element +")"));
            
                calculator.setInRadians(false);
                double radianValue = Math.toRadians(doubleValue);
                Assertions.assertEquals(String.valueOf(Math.cos(radianValue)), calculator.evaluate("cos("+ element + ")"));
            }
        }
    
        @Test
        public void acos() {
            for (String element: inputValues) {
                double doubleValue = Double.parseDouble(element);
                System.out.println(doubleValue + " " + element);
                calculator.setInRadians(true);
                Assertions.assertEquals(String.valueOf(Math.acos(doubleValue)), calculator.evaluate("acos(" + element +")"));
            
                calculator.setInRadians(false);
                double radianValue = Math.toRadians(doubleValue);
                Assertions.assertEquals(String.valueOf(Math.acos(radianValue)), calculator.evaluate("acos("+ element + ")"));
            }
        }
        @Test
        public void tan() {
            List<Double> inputValuesDoubles = new ArrayList<>();
            for (String element: inputValues) {
                double doubleValue = Double.parseDouble(element);
                System.out.println(doubleValue + " " + element);
                calculator.setInRadians(true);
                Assertions.assertEquals(String.valueOf(Math.tan(doubleValue)), calculator.evaluate("tan(" + element +")"));
            
                calculator.setInRadians(false);
                double radianValue = Math.toRadians(doubleValue);
                Assertions.assertEquals(String.valueOf(Math.tan(radianValue)), calculator.evaluate("tan("+ element + ")"));
            }
        }
    
        @Test
        public void atan() {
            List<Double> inputValuesDoubles = new ArrayList<>();
            for (String element: inputValues) {
                double doubleValue = Double.parseDouble(element);
                System.out.println(doubleValue + " " + element);
                calculator.setInRadians(true);
                Assertions.assertEquals(String.valueOf(Math.atan(doubleValue)), calculator.evaluate("atan(" + element +")"));
            
                calculator.setInRadians(false);
                double radianValue = Math.toRadians(doubleValue);
                Assertions.assertEquals(String.valueOf(Math.atan(radianValue)), calculator.evaluate("atan("+ element + ")"));
            }
        }
    }
    
    @TestFactory
    Iterator<DynamicTest> dynamicTrig() {
        ArrayList<DynamicTest> tests = new ArrayList<>();
        
        for (String element: inputValues) {
            double doubleValue = Double.parseDouble(element);
            tests.add(DynamicTest.dynamicTest("sin(" + element + ")",
                    () -> Assertions.assertEquals(String.valueOf(Math.sin(doubleValue)),
                            calculator.evaluate("sin(" + element +")"))));
            tests.add(DynamicTest.dynamicTest("cos(" + element + ")",
                    () -> Assertions.assertEquals(String.valueOf(Math.cos(doubleValue)),
                            calculator.evaluate("cos(" + element +")"))));
            tests.add(DynamicTest.dynamicTest("tan(" + element + ")",
                    () -> Assertions.assertEquals(String.valueOf(Math.tan(doubleValue)),
                            calculator.evaluate("tan(" + element +")"))));
        }
        Collections.sort(tests, Comparator.comparing(DynamicNode::getDisplayName));
        return tests.iterator();
    }
    
    private static Map<String, String> inputPairs() {
        List<String> shuffledInputValues = (List<String>) inputValues.clone();
        Collections.shuffle(shuffledInputValues);
        
        Map<String, String> inputMap = new HashMap<>();
        for (int i = 0; i < inputValues.size(); i++) {
            inputMap.put(inputValues.get(i), shuffledInputValues.get(i));
        }
        
        return inputMap;
    }
}
