package rpn.message;

import rpn.message.Message;

public class TokenMessage implements Message {
    public static final String MESSAGE_TYPE = "message";

    private final String token;
    private final String expressionId;

    public TokenMessage(String token, String expressionId) {
        this.token = token;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public String toString() {
        return "TokenMessage{" +
                "token='" + token + '\'' +
                ", expressionId='" + expressionId + '\'' +
                ", MESSAGE_TYPE='" + MESSAGE_TYPE + "'}";
    }
}
