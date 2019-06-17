package rpn.consumer;

import rpn.bus.Bus;
import rpn.message.Message;
import rpn.message.ResultCalculationMessage;

public class DisplayResultConsumer implements Consumer {

    public DisplayResultConsumer() {
    }

    @Override
    public void receive(Message message) {
        ResultCalculationMessage res = (ResultCalculationMessage) message;
        System.out.println("Calculation with id{ " + res.expressionId + " } result: " + res.result);
    }
}
