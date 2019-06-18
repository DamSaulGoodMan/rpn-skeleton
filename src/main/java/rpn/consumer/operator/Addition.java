package rpn.consumer.operator;

import rpn.bus.Bus;
import rpn.consumer.Consumer;
import rpn.message.ExceptionsMessage;
import rpn.message.Message;
import rpn.message.OperationMessage;
import rpn.message.OperatorMessage;
import rpn.utils.exception.MissingNumbersForOperation;


public class Addition implements Consumer {
    public final static String MESSAGE_TYPE = "+";

    private final Bus bus;
    public Addition(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperationMessage operationMessage = (OperationMessage) message;

        if(operationMessage.stackNumbers.size() < 2) {
            bus.publish(new ExceptionsMessage(new MissingNumbersForOperation(2,
                    operationMessage.stackNumbers.toString()),
                    message.id()));
            return;
        }

        double resAddition = operationMessage.stackNumbers.pop() + operationMessage.stackNumbers.pop();
        operationMessage.stackNumbers.push(resAddition);

        bus.publish(new OperatorMessage(operationMessage.stackNumbers, operationMessage.expressionId));
    }
}
