package shpp.app;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import static org.apache.activemq.ActiveMQConnection.DEFAULT_BROKER_URL;

public class SimpleQueue {
    private String queueName;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;
    private MessageConsumer consumer;

    public SimpleQueue(String queueName, String clientID) throws Exception {
        super();
        // The name of the queue.
        this.queueName = queueName;
        // URL of the JMS server is required to create connection factory.
        // DEFAULT_BROKER_URL is : tcp://localhost:61616 and is indicates that JMS
        // server is running on localhost
        connectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
        // Getting JMS connection from the server and starting it
        connection = connectionFactory.createConnection();
        connection.setClientID(clientID);
        connection.start();
        // Creating a non-transactional session to send/receive JMS message.
        session = connection.createSession(false, AUTO_ACKNOWLEDGE);
        // Destination represents here our queue ’MyFirstActiveMQ’ on the JMS
        // server.
        // The queue will be created automatically on the JSM server if its not already
        // created.
        destination = session.createQueue(this.queueName);
        // MessageProducer is used for sending (producing) messages to the queue.
        producer = session.createProducer(destination);
        // MessageConsumer is used for receiving (consuming) messages from the queue.
        consumer = session.createConsumer(destination);
    }

    public void send(POJO pojo) throws Exception {
        /*ObjectMessage message = session.createObjectMessage(pojo);

        producer.send(message);*/
    }

    public POJO receive() throws Exception {
        //todo: change receive to receive with long parameter
        Message message = consumer.receive();

        if (message instanceof ObjectMessage) {
            return (POJO) ((ObjectMessage) message).getObject();
        }
        return null;
    }

    public void close() throws JMSException {
        producer.close();
        producer = null;
        consumer.close();
        consumer = null;
        session.close();
        session = null;
        connection.close();
        connection = null;
    }
}