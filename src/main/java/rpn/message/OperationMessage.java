package rpn.message;

import java.util.Stack;

public class OperationMessage implements Message {
    public final String operator;
    public final Stack<Double> stackNumbers;

    public final String expressionId;

    public OperationMessage(String operator, Stack<Double> stackNumbers, String expressionId) {
        this.operator = operator;
        this.stackNumbers = stackNumbers;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return operator;
    }

    @Override
    public String id() {
        return expressionId;
    }

    @Override
    public String toString() {
        return "OperationMessage{" +
                "operator='" + operator + '\'' +
                ", stackNumbers=" + stackNumbers +
                ", expressionId='" + expressionId + '\'' +
                ", MESSAGE_TYPE='" + messageType() + '\'' +
                '}';
    }
}
