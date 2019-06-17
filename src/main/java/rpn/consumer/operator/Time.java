package rpn.consumer.operator;

import rpn.bus.Bus;
import rpn.consumer.Consumer;
import rpn.message.Message;
import rpn.message.OperationMessage;
import rpn.message.OperatorMessage;

public class Time implements Consumer {
    public final static String MESSAGE_TYPE = "TIME";

    private final Bus bus;
    public Time(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void receive(Message message) {
        OperationMessage operationMessage = (OperationMessage) message;

        double pow = operationMessage.stackNumbers.pop();
        double res = operationMessage.stackNumbers.pop();
        for (int cnt = 0; cnt < pow; cnt++) {
            res *= res;
        }
        operationMessage.stackNumbers.push(res);

        bus.publish(new OperatorMessage(operationMessage.stackNumbers, operationMessage.expressionId));
    }
}
