package rpn.consumer.operator;

import rpn.bus.Bus;
import rpn.consumer.Consumer;
import rpn.message.Message;
import rpn.message.OperationMessage;
import rpn.message.OperatorMessage;


public class Absolute implements Consumer {
    public final static String MESSAGE_TYPE = "ABS";

    private final Bus bus;
    public Absolute(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperationMessage operationMessage = (OperationMessage) message;

        double vAbs = Math.abs(operationMessage.stackNumbers.pop());
        operationMessage.stackNumbers.push(vAbs);
        bus.publish(new OperatorMessage(operationMessage.stackNumbers, operationMessage.expressionId));
    }
}
