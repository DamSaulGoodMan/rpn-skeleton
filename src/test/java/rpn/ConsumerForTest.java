package rpn;

import rpn.consumer.Consumer;
import rpn.message.Message;
import rpn.message.ResultCalculationMessage;

import java.util.Stack;

public class ConsumerForTest implements Consumer {

    private Stack<Double> stackResult;
    private String expressionId;

    public ConsumerForTest() {
    }

    @Override
    public void receive(Message message) {
        ResultCalculationMessage res = (ResultCalculationMessage) message;
        stackResult = res.result;
        expressionId = res.expressionId;
        System.out.println("Calculation with id{ " + expressionId + " } result: " + stackResult);
    }

    public Stack<Double> getStackResult() {
        return stackResult;
    }

    public String getExpressionId() {
        return expressionId;
    }
}

