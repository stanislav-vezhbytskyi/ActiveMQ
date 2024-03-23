package shpp.app;

import javax.jms.JMSException;
import java.util.Random;
import java.util.stream.Stream;

public class MessageGenerator {
    private DataGenerator dataGenerator;
    public MessageGenerator(){
        dataGenerator = new DataGenerator();
    }
    public void generateMessages(int numberMessages, Producer producer){

        Stream.generate(POJO::new).peek(pojo->{
            pojo.setCount( dataGenerator.generateCount());
            pojo.setEDDR( new Random().nextInt()+"");
            pojo.setName(dataGenerator.generateName(6,10));
            pojo.setCreatedAt(dataGenerator.generateDate());
        }).limit(numberMessages)
                .forEach(pojo -> {
            try {
                producer.sendObject(pojo);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            producer.sendObject(null);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
