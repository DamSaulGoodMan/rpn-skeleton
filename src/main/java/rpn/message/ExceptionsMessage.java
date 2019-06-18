package rpn.message;

public class ExceptionsMessage implements Message {
    public final static String MESSAGE_TYPE = "exception";

    public final Exception exception;
    public final String expressionId;

    public ExceptionsMessage(Exception exception, String expressionId) {
        this.exception = exception;
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
}
