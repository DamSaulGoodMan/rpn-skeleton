package rpn.consumer.operator;

import rpn.bus.Bus;
import rpn.consumer.Consumer;
import rpn.message.ExceptionsMessage;
import rpn.message.Message;
import rpn.message.OperationMessage;
import rpn.message.OperatorMessage;
import rpn.utils.exception.MissingNumbersForOperation;


public class Drop implements Consumer {
    public final static String MESSAGE_TYPE = "DROP";

    private final Bus bus;
    public Drop(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperationMessage operationMessage = (OperationMessage) message;

        if(operationMessage.stackNumbers.empty()) {
            bus.publish(new ExceptionsMessage(new MissingNumbersForOperation(1,
                    operationMessage.stackNumbers.toString()),
                    message.id()));
        }

        operationMessage.stackNumbers.pop();

        bus.publish(new OperatorMessage(operationMessage.stackNumbers, operationMessage.expressionId));
    }
}
