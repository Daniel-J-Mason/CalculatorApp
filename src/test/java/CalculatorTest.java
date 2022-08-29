import com.dev.calculatorapp.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    
    Calculator calculator = null;
    @BeforeEach
    public void initializeCalculator(){
        calculator = new Calculator();
    }
    
    @Test
    public void addition(){
        Assertions.assertEquals("40.0", calculator.evaluate("20 + 20"));
    }
    
    @Test
    public void subtraction(){
        Assertions.assertEquals("-20.0", calculator.evaluate("50 - 70"));
    }
    
    @Test
    public void multiplication(){
    }
    
    @Test
    public void division(){
        Assertions.assertEquals("4.5", calculator.evaluate("9 / 2"));
    }
    
    @Test
    public void complexProblems(){
        Assertions.assertEquals("25.5", calculator.evaluate("50 - 100 + 75.5"));
        Assertions.assertEquals("36.0", calculator.evaluate("3 x 5 + 21"));
        Assertions.assertEquals("18.0", calculator.evaluate("3 x ((6 + 6) / 2)"));
        Assertions.assertEquals("2.23606797749979", calculator.evaluate("√((((3 + 2)! / 2) / 60) + 4)"));
    }
    
    @Test
    public void trigonometry(){
        Assertions.assertEquals(String.valueOf(Math.sin(35)), calculator.evaluate("sin(35)"));
        
        calculator.setInRadians(false);
        Assertions.assertEquals(String.valueOf(Math.sin(Math.toRadians(35))), calculator.evaluate("sin(35)"));
    }
}
