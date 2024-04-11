package shpp.app;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.jms.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ConsumerTest {
    @Test
    public void receiveTest() throws JMSException {

        Session session = mock(Session.class);
        MessageConsumer messageConsumer = mock(MessageConsumer.class);
        Consumer testedConsumer = new Consumer(messageConsumer);

        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn("test text");

        when(session.createTextMessage()).thenReturn(textMessage);
        when(messageConsumer.receive()).thenReturn(textMessage);

        Assert.assertEquals("test text", testedConsumer.receive());

        verify(messageConsumer).receive();
        session.close();
        messageConsumer.close();
        testedConsumer.close();
    }
}
