package shpp.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.util.Random;
import java.util.stream.Stream;

public class MessageGenerator {
    private DataGenerator dataGenerator;

    public MessageGenerator() {
        dataGenerator = new DataGenerator();
    }

    public void generateMessages(int numberMessages, Producer producer, long poisonPill,String poisonPillMessage) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        long startTime = System.currentTimeMillis();
        Stream.generate(POJO::new)
                .takeWhile(pojo -> (System.currentTimeMillis() - startTime)/1000 < poisonPill)
                .peek(pojo -> {
                    pojo.setCount(dataGenerator.generateCount());
                    pojo.setEDDR(new Random().nextInt() + "");
                    pojo.setName(dataGenerator.generateName(6, 10));
                    pojo.setCreatedAt(dataGenerator.generateDate());
                }).limit(numberMessages)
                .forEach(pojo -> {
                    try {
                        //System.out.println(objectMapper.writeValueAsString(pojo));
                        producer.sendString(objectMapper.writeValueAsString(pojo));
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    } catch (JsonProcessingException e) {
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
