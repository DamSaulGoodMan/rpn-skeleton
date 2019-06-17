package rpn.consumer.operator;

import rpn.bus.Bus;
import rpn.consumer.Consumer;
import rpn.message.Message;
import rpn.message.OperationMessage;
import rpn.message.OperatorMessage;
import rpn.utils.exception.ZeroDivisionException;


public class Division implements Consumer {
    public final static String MESSAGE_TYPE = "/";

    private final Bus bus;
    public Division(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperationMessage operationMessage = (OperationMessage) message;

        Double divider =  operationMessage.stackNumbers.pop();
        if(divider.compareTo(0.0) == 0) throw new ZeroDivisionException();

        double res =  operationMessage.stackNumbers.pop() / divider;
        operationMessage.stackNumbers.push(res);

        bus.publish(new OperatorMessage(operationMessage.stackNumbers, operationMessage.expressionId));
    }
}