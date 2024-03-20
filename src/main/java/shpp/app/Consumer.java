package shpp.app;

import javax.jms.*;

public class Consumer {

    private MessageConsumer consumer;
    public Consumer(MessageConsumer messageConsumer){
        this.consumer = messageConsumer;
    }
    public POJO receive() throws JMSException {
        Message message = consumer.receive();

        if (message instanceof ObjectMessage) {
            return (POJO) ((ObjectMessage) message).getObject();
        }
        return null;
    }
    public void close() throws JMSException {
        consumer.close();
    }
}
