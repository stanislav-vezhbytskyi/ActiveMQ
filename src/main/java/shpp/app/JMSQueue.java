package shpp.app;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

public class JMSQueue {
    private String queueName;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    public void init(String queueName, String clientID, String brokerURL) throws JMSException {
        this.queueName = queueName;
        // URL of the JMS server is required to create connection factory.
        // DEFAULT_BROKER_URL is : tcp://localhost:61616 and is indicates that JMS
        // server is running on localhost
        connectionFactory = new ActiveMQConnectionFactory(brokerURL);
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
        destination = session.createQueue(queueName);
    }

    public void close() throws JMSException {
        session.close();
        session = null;
        connection.close();
        connection = null;
    }

    public Producer createProducer() throws JMSException {
        return new Producer(session.createProducer(destination),session);
    }

    public Consumer createConsumer() throws JMSException {
        return new Consumer(session.createConsumer(destination));
    }
}
