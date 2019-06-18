package rpn.message;

public class ExpressionMessage implements Message {
    public static final String MESSAGE_TYPE = "expression";

    private final String expression;
    private final String expressionId;

    public ExpressionMessage(String expression, String expressionId) {
        this.expression = expression;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public String id() {
        return expressionId;
    }

    public String expression() {
        return expression;
    }
    public String expressionId() {
        return expressionId;
    }

    @Override
    public String toString() {
        return "ExpressionMessage{" +
                "expression='" + expression + '\'' +
                ", expressionId='" + expressionId + '\'' +
                ", MESSAGE_TYPE='" + MESSAGE_TYPE + "'}";
    }
}