package rpn.message;

public class EndOfToken implements Message {
    public EndOfToken(String s) {
    }

    @Override
    public String messageType() {
        return null;
    }

    @Override
    public String toString() {
        return "EndOfToken{}";
    }
}