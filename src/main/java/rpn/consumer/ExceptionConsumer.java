package rpn.consumer;

import rpn.message.ExceptionsMessage;
import rpn.message.Message;


public class ExceptionConsumer implements Consumer {
    @Override
    public void receive(Message message) {
        ExceptionsMessage exceptionsMessage = (ExceptionsMessage) message;

        System.err.println("Exception retrieve from message with id{'" + exceptionsMessage.id() + "}:\n/!\\ " +
                exceptionsMessage.exception);

        for (StackTraceElement ste : exceptionsMessage.exception.getStackTrace()) {
            System.err.println(ste);
        }
    }
}
