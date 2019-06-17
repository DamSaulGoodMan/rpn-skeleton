package rpn.consumer.operator;

import rpn.bus.Bus;
import rpn.consumer.Consumer;
import rpn.message.Message;
import rpn.message.OperationMessage;
import rpn.message.OperatorMessage;


public class Addition implements Consumer {
    public final static String MESSAGE_TYPE = "+";

    private final Bus bus;
    public Addition(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperationMessage operationMessage = (OperationMessage) message;

        double resAddition = operationMessage.stackNumbers.pop() + operationMessage.stackNumbers.pop();
        operationMessage.stackNumbers.push(resAddition);

        bus.publish(new OperatorMessage(operationMessage.stackNumbers, operationMessage.expressionId));
    }
}