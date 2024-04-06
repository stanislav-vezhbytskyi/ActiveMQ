package shpp.app;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.jms.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ConsumerTest {
    private Session session;
    private MessageConsumer messageConsumer;
    private Consumer testedConsumer ;
    @Before
    public void setUp() throws JMSException {
        session = mock(Session.class);
        messageConsumer = mock(MessageConsumer.class);
        testedConsumer = new Consumer(messageConsumer);

        when(session.createConsumer(any())).thenReturn(messageConsumer);
    }
    @After
    public void tearDown() throws JMSException {
        session.close();
        messageConsumer.close();
        testedConsumer.close();
    }
    @Test
    public void receiveTest() throws JMSException {
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn("test text");

        when(session.createTextMessage()).thenReturn(textMessage);
        when(messageConsumer.receive()).thenReturn(textMessage);

        Assert.assertEquals("test text",testedConsumer.receive());

        verify(messageConsumer).receive();
    }
}
