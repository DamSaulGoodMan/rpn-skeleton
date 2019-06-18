package rpn.message;

import java.util.Stack;

public class OperatorMessage implements Message {
    public static final String MESSAGE_TYPE = "operator";
    public final Stack<Double> stackNumbers;

    private final String expressionId;

    public OperatorMessage(Stack<Double> stackNumbers, String expressionId) {
        this.stackNumbers = stackNumbers;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return null;
    }

    @Override
    public String id() {
        return expressionId;
    }

    @Override
    public String toString() {
        return "OperatorMessage{" +
                "stackNumbers=" + stackNumbers +
                ", expressionId='" + expressionId + '\'' +
                ", MESSAGE_TYPE='" + MESSAGE_TYPE + '\'' +
                '}';
    }
}
