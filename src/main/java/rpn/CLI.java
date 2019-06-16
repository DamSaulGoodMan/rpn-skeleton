package rpn;

import rpn.bus.InMemoryBus;
import rpn.message.ExpressionMessage;
import rpn.consumer.TokenizerConsumer;

import java.util.UUID;

public class CLI {
    public static void main(String[] args) {
        InMemoryBus bus = new InMemoryBus();
        bus.subscribe(ExpressionMessage.MESSAGE_TYPE, new TokenizerConsumer(bus));
        //bus.subscribe(ExpressionMessage.MESSAGE_TYPE, new TokenizerConsumer(bus));

        String expressionId = UUID.randomUUID().toString();
        ExpressionMessage expressionMessage = new ExpressionMessage("1 2 +", expressionId);
        bus.publish(expressionMessage);
        System.out.println(expressionMessage);
    }
}