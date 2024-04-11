package shpp.app;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class App {
    private static final String PROPERTY_FILE_NAME = "app.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static final JMSQueue queue = new JMSQueue();
    private static final String POISON_PILL_MESSAGE = "THE END OF THE QUEUE";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        ConfigManager configManager = new ConfigManager();
        configManager.readConfiguration(PROPERTY_FILE_NAME);

        try {
            queue.init(configManager.getQueueName(), configManager.getClientId(), configManager.getBrokerURL());

            Producer producer = queue.createProducer();

            long beforeGeneration = System.currentTimeMillis();

            //generate messages
            MessageGenerator messageGenerator = new MessageGenerator();
            messageGenerator.generateMessages(configManager.getNumberSentMessages(), producer,
                    configManager.getPoisonPill(), POISON_PILL_MESSAGE);
            LOGGER.info("Generation time {}", System.currentTimeMillis() - beforeGeneration);

            //read messages
            Consumer consumer = queue.createConsumer();
            MessageHandler messageHandler = new MessageHandler();
            messageHandler.readMessages(consumer, POISON_PILL_MESSAGE);


            //write information about pojo to files
            Writer writer = new FileWriter("out/csvWithCorrectData");
            StatefulBeanToCsv statefulBeanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            statefulBeanToCsv.write(messageHandler.getListWithCorrectPojo());


            MyCSVWriter myCSVWriter = new MyCSVWriter();

            myCSVWriter.writeIncorrectData(messageHandler.getListWithIncorrectPojo(),
                    messageHandler.getListWithErrors(),new CSVWriter(new FileWriter("out/csvWithIncorrectData")));

            writer.close();
            consumer.close();
            producer.close();
            queue.close();
        } catch (CsvRequiredFieldEmptyException | JMSException | CsvDataTypeMismatchException | IOException |
                 IllegalAccessException e) {
            LOGGER.error(e.toString(),e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info(String.valueOf(endTime - startTime));
    }
}
