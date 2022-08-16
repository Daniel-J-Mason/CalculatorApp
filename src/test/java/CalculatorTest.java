import com.dev.calculatorapp.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    
    Calculator calculator = null;
    @BeforeEach
    public void initializeCalculator(){
        calculator = new Calculator();
    }
    
    @Test
    public void addition(){
        Assertions.assertEquals("40.0", calculator.evaluate("20+20"));
    }
    
    @Test
    public void subtraction(){
        Assertions.assertEquals("-20.0", calculator.evaluate("50-70"));
    }
    
    @Test
    public void multiplication(){
    }
    
    @Test
    public void division(){
    
    }
    
    @Test
    public void complexProblems(){
        Assertions.assertEquals("25.5", calculator.evaluate("50-100+75.5"));
        Assertions.assertEquals("36.0", calculator.evaluate("3x5+21"));
        Assertions.assertEquals("36.0", calculator.evaluate("3x(6+6)"));
    }
}
