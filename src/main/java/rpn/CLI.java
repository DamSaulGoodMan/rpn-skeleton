package rpn;

import rpn.bus.InMemoryBus;
import rpn.consumer.DisplayResultConsumer;
import rpn.consumer.operator.*;
import rpn.message.*;
import rpn.consumer.TokenizerConsumer;
import rpn.consumer.Calculator;

import java.util.UUID;


public class CLI {
    public static void main(String[] args) {
        InMemoryBus bus = new InMemoryBus();
        //bus.activateLog(true);

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

        bus.subscribe(ResultCalculationMessage.MESSAGE_TYPE, new DisplayResultConsumer());

        String expressionId = UUID.randomUUID().toString();
        ExpressionMessage expressionMessage = new ExpressionMessage("1 2 -", expressionId);
        bus.publish(expressionMessage);
    }
}