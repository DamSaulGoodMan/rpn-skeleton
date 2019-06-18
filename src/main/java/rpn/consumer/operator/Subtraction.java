package rpn.consumer.operator;

import rpn.bus.Bus;
import rpn.consumer.Consumer;
import rpn.message.ExceptionsMessage;
import rpn.message.Message;
import rpn.message.OperationMessage;
import rpn.message.OperatorMessage;
import rpn.utils.exception.MissingNumbersForOperation;

public class Subtraction implements Consumer {
    public final static String MESSAGE_TYPE = "-";

    private final Bus bus;
    public Subtraction(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperationMessage operationMessage = (OperationMessage) message;

        if(operationMessage.stackNumbers.empty()) {
            bus.publish(new ExceptionsMessage(new MissingNumbersForOperation(2,
                    operationMessage.stackNumbers.toString()),
                    message.id()));
        }

        double res = -operationMessage.stackNumbers.pop() + operationMessage.stackNumbers.pop();
        operationMessage.stackNumbers.push(res);

        bus.publish(new OperatorMessage(operationMessage.stackNumbers, operationMessage.expressionId));
    }
}