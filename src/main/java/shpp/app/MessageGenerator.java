package shpp.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jms.JMSException;
import java.util.Random;
import java.util.stream.Stream;

public class MessageGenerator {
    private final DataGenerator dataGenerator;

    public MessageGenerator() {
        dataGenerator = new DataGenerator();
    }

    public void generateMessages(int numberMessages, Producer producer, long poisonPill, String poisonPillMessage) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        Random rand = new Random();

        long startTime = System.currentTimeMillis();
        Stream.generate(POJO::new)
                .takeWhile(pojo -> (System.currentTimeMillis() - startTime) / 1000 < poisonPill)
                .peek(pojo -> pojo.setCount(dataGenerator.generateCount())
                        .setEDDR(rand.nextInt() + "")
                        .setName(dataGenerator.generateName(6, 10))
                        .setCreatedAt(dataGenerator.generateDate())).limit(numberMessages)
                .forEach(pojo -> {
                    try {
                        producer.sendString(objectMapper.writeValueAsString(pojo));
                    } catch (JMSException | JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        try {
            producer.sendString(poisonPillMessage);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
