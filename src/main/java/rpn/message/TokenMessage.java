package rpn.message;

import rpn.message.Message;

public class TokenMessage implements Message {
    public static final String MESSAGE_TYPE = "message";

    private final String token;
    public final String expressionId;

    public TokenMessage(String token, String expressionId) {
        this.token = token;
        this.expressionId = expressionId;
    }

    @Override
    public String messageType() {
        return MESSAGE_TYPE;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "TokenMessage{" +
                "token='" + token + '\'' +
                ", expressionId='" + expressionId + '\'' +
                ", MESSAGE_TYPE='" + MESSAGE_TYPE + "'}";
    }
}
