package shpp.app;

import javax.jms.JMSException;
import java.time.LocalDate;
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
            pojo.setEddr( new Random().nextInt()+"");
            pojo.setName(dataGenerator.generateName(0,10));
            pojo.setCreatedAt(dataGenerator.generateDate());
        }).limit(numberMessages)
                .forEach(pojo -> {
            try {
                producer.sendObject(pojo);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
