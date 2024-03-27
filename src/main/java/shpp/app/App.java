package shpp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class App {
    private static final int DEFAULT_NUMBER_SENT_MESSAGES = 100;
    private static final String PROPERTY_FILE_NAME = "app.properties";
    private static final String CLIENT_ID = "ID:DESKTOP-ME97MLP-55962-1708879633133-0:1";
    private static final String QUEUE_NAME = "MyFirstActiveMQ";
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static final JMSQueue queue = new JMSQueue();
    private static final String POISON_PILL_MESSAGE = "THE END OF THE QUEUE";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        String queueName = "QUEUE_NAME";
        String clientID = "CLIENT_ID";
        String brokerURL = "DEFAULT_BROKER_URL";
        long poisonPill = 100;

        int numberSentMessages = DEFAULT_NUMBER_SENT_MESSAGES;

        if (System.getProperty("N") != null) {
            numberSentMessages = Integer.parseInt(System.getProperty("N"));
        }

        PropertyReader propertyReader = new PropertyReader();
        Properties properties = propertyReader.readProperties(PROPERTY_FILE_NAME);
        if (properties != null && !properties.isEmpty()) {
            queueName = properties.getProperty("queueName");
            clientID = properties.getProperty("clientID");
            brokerURL = properties.getProperty("brokerURL");
            poisonPill = Long.valueOf(properties.getProperty("poisonPill"));
        }

        try {
            queue.init(queueName, clientID, brokerURL);

            Producer producer = queue.createProducer();

            long beforeGeneration = System.currentTimeMillis();

            MessageGenerator messageGenerator = new MessageGenerator();
            messageGenerator.generateMessages(numberSentMessages, producer, poisonPill, POISON_PILL_MESSAGE);

            LOGGER.info("Generation time {}", System.currentTimeMillis() - beforeGeneration);

            Consumer consumer = queue.createConsumer();
            MessageReader messageReader = new MessageReader();
            messageReader.readMessages(consumer, POISON_PILL_MESSAGE);
            consumer.close();
            producer.close();
            queue.close();
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info(String.valueOf(endTime - startTime));
    }
}

