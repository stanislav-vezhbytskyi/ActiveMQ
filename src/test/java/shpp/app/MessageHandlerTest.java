package shpp.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.jms.JMSException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MessageHandlerTest {
    private MessageHandler messageHandler;

    @Before
    public void setUp() {
        messageHandler = new MessageHandler();
    }

    @Test
    public void correctListsTest() throws JMSException, JsonProcessingException, IllegalAccessException {
        POJO correctPojo = new POJO("nameaaaj", "5453", 434, LocalDate.MAX);
        POJO incorrectPojo = new POJO("", "5453", 434, LocalDate.MAX);

        List<POJO> expectedCorrectList = new ArrayList<>(List.of(correctPojo));
        List<POJO> expectedIncorrectList = new ArrayList<>(List.of(incorrectPojo));

        Consumer consumer = mock(Consumer.class);

        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

        when(consumer.receive()).thenReturn(objectMapper.writeValueAsString(correctPojo),
                objectMapper.writeValueAsString(incorrectPojo),
                "poison pill");

        messageHandler.readMessages(consumer,"poison pill");

        Assert.assertEquals(expectedCorrectList,messageHandler.getListWithCorrectPojo());
        Assert.assertEquals(expectedIncorrectList,messageHandler.getListWithIncorrectPojo());
    }

    @Test
    public void receiveMessagesTest() throws JMSException, IllegalAccessException, IOException {
        String poisonPillMessage = "PoisonPill";
        List<String> messages = new LinkedList<>();
        messages.add("{\"name\":\"1\",\"eddr\":\"1\",\"count\":\"1\",\"createdAt\":\"2020-03-16\"}");
        messages.add("{\"name\":\"1\",\"eddr\":\"1\",\"count\":\"1\",\"createdAt\":\"2020-03-16\"}");
        messages.add(poisonPillMessage);

        Consumer mockConsumer = mock(Consumer.class);

        when(mockConsumer.receive()).thenReturn(messages.remove(0), messages.remove(0), messages.remove(0));

        messageHandler.readMessages(mockConsumer, poisonPillMessage);

        verify(mockConsumer, times(3)).receive();
    }
}

