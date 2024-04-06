package shpp.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.jms.JMSException;

import static org.mockito.Mockito.*;

public class MessageGeneratorTest {
    private int numberMessages;
    private Producer producer;
    private MessageGenerator messageGenerator;
    @Before
    public void setUp() {
        numberMessages = 1000;
        producer = mock(Producer.class);
        messageGenerator = new MessageGenerator();
    }
    @After
    public void tearDown() throws JMSException {
        producer.close();
    }
    @Test
    public void sendPoisonPillTest() throws JMSException {
        String poisonPillMessage = "poison pill";

        messageGenerator.generateMessages(numberMessages, producer, 100, poisonPillMessage);

        verify(producer, times(numberMessages + 1)).sendString(any());
    }
    @Test(expected = RuntimeException.class)
    public void generateMessagesHandlesExceptionTest() throws JMSException {
        Mockito.doThrow(new JMSException("Mock JMS Exception")).when(producer).
                sendString(Mockito.anyString());
        long poisonPill = 10000;
        String poisonPillMessage = "Poison Pill";

        messageGenerator.generateMessages(numberMessages, producer, poisonPill, poisonPillMessage);
    }
    @Test
    public void generateMessageSendPillMessageTest() throws JMSException {
        long poisonPill = 1000;
        String poisonPillMessage = "poison pill";

        messageGenerator.generateMessages(numberMessages,producer,poisonPill,poisonPillMessage);

        verify(producer).sendString(poisonPillMessage);
    }

}
