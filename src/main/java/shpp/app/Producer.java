package shpp.app;

import javax.jms.*;

public class Producer {
    private Session session;
    private MessageProducer producer;

    public Producer(MessageProducer producer, Session session) {
        this.session = session;
        this.producer = producer;
    }

    public void close() throws JMSException {
        producer.close();
    }

    public void sendString(String message) throws JMSException {
        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);
    }
}
