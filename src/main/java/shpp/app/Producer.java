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

    public void sendObject(POJO pojo) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage(pojo);
        producer.send(objectMessage);
    }
}
