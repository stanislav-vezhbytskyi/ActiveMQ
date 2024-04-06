package shpp.app;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jms.*;

import static org.mockito.Mockito.*;

public class ProducerTest {
    private MessageProducer messageProducer;
    private Session session;
    private Producer testetProducer;
    @Before
    public void setUp() throws JMSException {
        session = mock(Session.class);
        messageProducer = mock(MessageProducer.class);
        testetProducer = new Producer(messageProducer, session);

        when(session.createProducer(any())).thenReturn(messageProducer);
    }
    @After
    public void tearDown() throws JMSException {
        session.close();
        messageProducer.close();
        testetProducer.close();
    }
    @Test
    public void testProducer() throws JMSException {
        String message = "test message";
        TextMessage textMessage = mock(TextMessage.class);
        when(session.createTextMessage(message)).thenReturn(textMessage);

        testetProducer.sendString(message);

        verify(session).createTextMessage("test message");
        verify(messageProducer).send(textMessage);
    }
}
