package rpn;

import org.assertj.core.data.Offset;
import org.junit.Test;
import rpn.bus.InMemoryBus;
import rpn.consumer.Calculator;
import rpn.consumer.TokenizerConsumer;
import rpn.consumer.operator.*;
import rpn.message.*;
import rpn.utils.exception.InvalidMessageToken;
import rpn.utils.exception.ZeroDivisionException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class CalculationTest {

    private final ConsumerForTest consumerTest = new ConsumerForTest();

    private InMemoryBus createTestBus() {
        InMemoryBus bus = new InMemoryBus();

        bus.subscribe(ExpressionMessage.MESSAGE_TYPE, new TokenizerConsumer(bus));

        Calculator calculator = new Calculator(bus);
        bus.subscribe(TokenMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(OperatorMessage.MESSAGE_TYPE, calculator);
        bus.subscribe(EndOfToken.MESSAGE_TYPE, calculator);

        bus.subscribe(Addition.MESSAGE_TYPE, new Addition(bus));
        bus.subscribe(Subtraction.MESSAGE_TYPE, new Subtraction(bus));
        bus.subscribe(Multiplication.MESSAGE_TYPE, new Multiplication(bus));
        bus.subscribe(Division.MESSAGE_TYPE, new Division(bus));
        bus.subscribe(Absolute.MESSAGE_TYPE, new Absolute(bus));
        bus.subscribe(Swap.MESSAGE_TYPE, new Swap(bus));
        bus.subscribe(Time.MESSAGE_TYPE, new Time(bus));
        bus.subscribe(Drop.MESSAGE_TYPE, new Drop(bus));

        bus.subscribe(ResultCalculationMessage.MESSAGE_TYPE, consumerTest);

        return bus;
    }

    private final InMemoryBus bus = createTestBus();


    private final String expressionIdTest = UUID.randomUUID().toString();
    private final static Offset<Double> offsetMillionthAccuracy = Offset.offset(0.00001);

    @Test
    public void should_evaluate_single_digit_constant() {
        bus.publish(new ExpressionMessage("5", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(5, offsetMillionthAccuracy);
    }

    @Test
    public void should_evaluate_simple_addition() {
        bus.publish(new ExpressionMessage("20 2 +", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(22, offsetMillionthAccuracy);
    }

    @Test
    public void should_evaluate_more_complex_addition() {
        bus.publish(new ExpressionMessage("2 3 5 + +", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(10, offsetMillionthAccuracy);
    }

    @Test
    public void should_evaluate_simple_subtraction() {
        bus.publish(new ExpressionMessage("17 5 -", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(12, offsetMillionthAccuracy);
    }

    @Test
    public void should_evaluate_simple_multiplication() {
        bus.publish(new ExpressionMessage("17 5 *", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(85, offsetMillionthAccuracy);
    }

    @Test
    public void should_evaluate_entire_division() {
        bus.publish(new ExpressionMessage("17 5 /", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(3.4, offsetMillionthAccuracy);
    }

    @Test
    public void should_evaluate_simple_division() {
        bus.publish(new ExpressionMessage("2 3 /", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(0.66666, offsetMillionthAccuracy);
    }

    @Test (expected = ZeroDivisionException.class)
    public void should_raise_exception_for_banned_division() {
        bus.publish(new ExpressionMessage("17 0 /", expressionIdTest));
    }

    @Test
    public void should_evaluate_more_complex_operation() {
        bus.publish(new ExpressionMessage("2 3 - 16 2 4 * / +", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(1, offsetMillionthAccuracy);
    }

    @Test
    public void should_contain_2_numbers() {
        bus.publish(new ExpressionMessage("0 1", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(1, offsetMillionthAccuracy);
        assertThat(consumerTest.getStackResult().pop()).isCloseTo(0, offsetMillionthAccuracy);
        assertThat(consumerTest.getStackResult().size()).isEqualTo(0);
    }

    @Test
    public void should_return_abs_value() {
        bus.publish(new ExpressionMessage("-1 ABS", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(1, offsetMillionthAccuracy);
    }

    @Test
    public void should_clean_stack() {
        bus.publish(new ExpressionMessage("0 DROP", expressionIdTest));

        assertThat(consumerTest.getStackResult().size()).isEqualTo(0);
    }

    @Test
    public void should_rest_1_value() {
        bus.publish(new ExpressionMessage("0 1 DROP", expressionIdTest));

        assertThat(consumerTest.getStackResult().size()).isEqualTo(1);
        assertThat(consumerTest.getStackResult().pop()).isCloseTo(0, offsetMillionthAccuracy);
    }

    @Test
    public void should_rest_swap_of_two_number() {
        bus.publish(new ExpressionMessage("0 1 SWAP", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(0, offsetMillionthAccuracy);
        assertThat(consumerTest.getStackResult().pop()).isCloseTo(1, offsetMillionthAccuracy);
    }

    @Test
    public void should_rest_swap_of_the_two_first_number() {
        bus.publish(new ExpressionMessage("0 1 SWAP 2", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(2, offsetMillionthAccuracy);
        assertThat(consumerTest.getStackResult().pop()).isCloseTo(0, offsetMillionthAccuracy);
        assertThat(consumerTest.getStackResult().pop()).isCloseTo(1, offsetMillionthAccuracy);
    }

    @Test
    public void should_rest_swap_of_the_two_last_number() {
        bus.publish(new ExpressionMessage("0 1 2 SWAP", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(1, offsetMillionthAccuracy);
        assertThat(consumerTest.getStackResult().pop()).isCloseTo(2, offsetMillionthAccuracy);
        assertThat(consumerTest.getStackResult().pop()).isCloseTo(0, offsetMillionthAccuracy);
    }

    @Test
    public void should_rest_the_pow_of_10_exponent_3() {
        bus.publish(new ExpressionMessage("10 3 TIME", expressionIdTest));

        assertThat(consumerTest.getStackResult().pop()).isCloseTo(1000, offsetMillionthAccuracy);
    }

//    @Test (expected = InvalidMessageToken.class)
//    public void should_raise_exception_for_invalid_operator() {
//        ExpressionMessage expressionMessage = new ExpressionMessage("", expressionIdTest);
//        bus.publish(expressionMessage);
//
//        assertThat(consumerTest.getStackResult().pop()).isCloseTo(, offsetMillionthAccuracy);
//        try {
//            assertThat(evaluate(new String[]{"1", "1", "%"}, dico)).isEqualTo("0");
//        }
//        catch (InvalidMessageToken ikfo) {
//            ikfo.printStackTrace();
//            throw ikfo;
//        }
//    }
}