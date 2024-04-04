package shpp.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.runners.util.TestMethodsFinder;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import static org.mockito.Mockito.*;

public class ProducerTest {
    private Producer producer;
    private Session session;
    private MessageProducer messageProducer;
    @Before
    public void setUp(){
        session = mock(Session.class);
        messageProducer = mock(MessageProducer.class);
        producer = new Producer(messageProducer,session);

    }
    @After
    public void tearDown() throws JMSException {
        producer.close();
    }
    @Test
    public void testProducer() throws JMSException {
        String message = "test message";
        TextMessage textMessage = mock(TextMessage.class);
        when(session.createTextMessage(message)).thenReturn(textMessage);

        producer.sendString(message);


    }
}
