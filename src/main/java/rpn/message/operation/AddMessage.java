package rpn.message.operation;

import rpn.message.Message;

public class AddMessage implements Message {
    public static final String MESSAGE_TYPE = "+";

    private final double[] number;
    private final String expressionId;

    public AddMessage(double[] number, String expressionId) {
        this.number = number;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public double[] getNumber() {
        return number;
    }

    public String getExpressionId() {
        return expressionId;
    }
}
