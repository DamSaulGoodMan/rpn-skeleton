package rpn;

import org.assertj.core.data.Offset;
import org.junit.Test;
import rpn.bus.InMemoryBus;
import rpn.utils.exception.InvalidKeyForOperation;

import static org.assertj.core.api.Assertions.assertThat;


public class CLITest {

    InMemoryBus bus = new InMemoryBus();

    @Test
    public void should_evaluate_single_digit_constant() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"5"}, dico).pop()).isEqualTo(5);
    }

    @Test
    public void should_evaluate_multiple_digits_constant() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"17"}, dico).pop()).isEqualTo(17);
    }

    @Test
    public void should_evaluate_simple_addition() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"17", "5", "+"}, dico).pop()).isEqualTo(22);
    }

    @Test
    public void should_evaluate_more_complex_addition() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"2", "3", "5", "+", "+"}, dico).pop()).isEqualTo(10);
    }

    @Test
    public void should_evaluate_simple_subtraction() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"17", "5", "-"}, dico).pop()).isEqualTo(12);
    }

    @Test
    public void should_evaluate_simple_multiplication() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"17", "5", "*"}, dico).pop()).isEqualTo(85);
    }

    @Test
    public void should_evaluate_entire_division() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"17", "5", "/"}, dico).pop()).isCloseTo(3.4, Offset.offset(0.1));
    }

    @Test
    public void should_evaluate_simple_division() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"2", "3", "/"}, dico).pop()).isCloseTo(0.66666, Offset.offset(0.00001));
    }

    @Test (expected = ZeroDivisionException.class)
    public void should_raise_exception_for_banned_division() throws InvalidKeyForOperation {
        evaluate(new String[]{"17", "0", "/"}, dico);
    }

    @Test
    public void should_evaluate_more_complex_operation() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"2", "3", "-", "16", "2", "4", "*", "/", "+"}, dico).pop()).isEqualTo(1);
    }

    @Test
    public void should_contain_2_numbers() throws InvalidKeyForOperation {
        assertThat(evaluate(new String[]{"0", "1"}, dico)).contains(1d, 0d);
    }

    @Test
    public void should_return_abs_value() throws InvalidKeyForOperation {
        dico.addOperation("ABS", new Absolute());
        assertThat(evaluate(new String[]{"-1", "ABS"}, dico).pop()).isEqualTo(1d);
    }

    @Test
    public void should_clean_stack() throws InvalidKeyForOperation {
        dico.addOperation("Drop", new Drop());
        assertThat(evaluate(new String[]{"0", "Drop"}, dico).size()).isEqualTo(0);
    }

    @Test
    public void should_rest_1_value() throws InvalidKeyForOperation {
        dico.addOperation("Drop", new Drop());
        assertThat(evaluate(new String[]{"0", "1", "Drop"}, dico).size()).isEqualTo(1);
        assertThat(evaluate(new String[]{"0", "1", "Drop"}, dico).pop()).isEqualTo(0);
    }

    @Test (expected = InvalidKeyForOperation.class)
    public void should_raise_exception_for_invalid_operator() throws InvalidKeyForOperation {
        try {
            assertThat(evaluate(new String[]{"1", "1", "%"}, dico)).isEqualTo("0");
        }
        catch (InvalidKeyForOperation ikfo) {
            ikfo.printStackTrace();
            throw ikfo;
        }
    }
}