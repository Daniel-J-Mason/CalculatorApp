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
        calculator.calculate(20, 20, "+");
        Assertions.assertEquals(40, calculator.getTotal());
        calculator.calculate(20, "+");
        Assertions.assertEquals(60, calculator.getTotal());
        calculator.calculate(-60 ,"+");
        Assertions.assertEquals(0, calculator.getTotal());
    }
    
    @Test
    public void subtraction(){
        calculator.calculate(20, 20, "-");
        Assertions.assertEquals(0, calculator.getTotal());
        calculator.calculate(27, "-");
        Assertions.assertEquals(-27, calculator.getTotal());
        calculator.calculate(-27, "-");
        Assertions.assertEquals(0, calculator.getTotal());
    }
    
    @Test
    public void multiplication(){
    }
    
    @Test
    public void division(){
    
    }
    
    @Test
    public void printouts(){
    }
}
