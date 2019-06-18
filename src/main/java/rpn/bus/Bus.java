package rpn.bus;

import rpn.consumer.Consumer;
import rpn.message.Message;
import rpn.utils.exception.InvalidMessageToken;

public interface Bus {
    void publish(Message message);
    void subscribe(String eventType, Consumer consumer);
}