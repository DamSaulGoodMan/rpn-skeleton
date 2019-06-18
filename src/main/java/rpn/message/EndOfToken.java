package rpn.message;

public class EndOfToken implements Message {
    public final static String MESSAGE_TYPE = "eot";
    public final String expressionId;

    public EndOfToken(String expressionId) {
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

    @Override
    public String toString() {
        return "EndOfToken{" +
                "expressionId='" + expressionId + '\'' +
                "MESSAGE_TYPE='" + MESSAGE_TYPE + '\'' +
                "}";
    }
}