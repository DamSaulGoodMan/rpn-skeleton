package rpn.bus;

import rpn.consumer.Consumer;
import rpn.consumer.ExceptionConsumer;
import rpn.message.ExceptionsMessage;
import rpn.message.Message;
import rpn.utils.exception.InvalidMessageToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryBus implements Bus {

    private final Map<String, List<Consumer>> consumersByType = new HashMap<>();
    private boolean enableLog = false;

    public InMemoryBus() {
        List<Consumer> consumers = consumersByType.computeIfAbsent(ExceptionsMessage.MESSAGE_TYPE, k -> new ArrayList<>());
        consumers.add(new ExceptionConsumer());
    }

    @Override
    public void publish(Message message) {
        if(enableLog) System.out.println(message);

        List<Consumer> consumers = consumersByType.get(message.messageType());
        if(consumers == null) {
            publish(new ExceptionsMessage(
                    new InvalidMessageToken(message.messageType(), consumersByType.keySet()), message.id()));
            return;
        }

        consumers.forEach(c -> c.receive(message));
    }

    @Override
    public void subscribe(String messageType, Consumer consumer) {
        List<Consumer> consumers = consumersByType.computeIfAbsent(messageType, k -> new ArrayList<>());
        consumers.add(consumer);
    }

    public void activateLog(boolean enableLog) {
        this.enableLog = enableLog;
    }
}