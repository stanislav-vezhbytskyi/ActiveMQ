package shpp.app;

import javax.jms.*;

public class Consumer {

    private MessageConsumer consumer;

    public Consumer(MessageConsumer messageConsumer) {
        this.consumer = messageConsumer;
    }

    public String receive() throws JMSException {
        Message message = consumer.receive();

        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        }
        return null;
    }

    public void close() throws JMSException {
        consumer.close();
    }
}
