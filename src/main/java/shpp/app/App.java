package shpp.app;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Properties;
import java.util.Set;

public class App {
    private static final int DEFAULT_NUMBER_SENT_MESSAGES = 100;
    private static final String PROPERTY_FILE_NAME = "app.properties";
    private static final String CLIENT_ID = "ID:DESKTOP-ME97MLP-55962-1708879633133-0:1";
    private static final String QUEUE_NAME = "MyFirstActiveMQ";
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static final JMSQueue queue = new JMSQueue();

    public static void main(String[] args) throws IllegalAccessException {
      /*  POJO pojo = new POJO();
        pojo.count = 5;
        pojo.EDDR = "fffffff";
        pojo.name = "a";
        pojo.createdAt = null;
        IsContainSymbolValidator symbolValidator = new IsContainSymbolValidator();

        System.out.println(symbolValidator.isValid(pojo));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        Validator validator = factory.getValidator();

        System.out.println();

        Set<ConstraintViolation<POJO>> violations = validator.validate(pojo);
        for (ConstraintViolation<POJO> violation : violations) {
            System.out.println(violation.getMessage());
        }*/
        String queueName = "QUEUE_NAME";
        String clientID = "CLIENT_ID";
        String brokerURL = "DEFAULT_BROKER_URL";

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
        }

        DataGenerator dataGenerator = new DataGenerator();
        LOGGER.info(dataGenerator.generateName(5, 10));
        try {
            queue.init(queueName, clientID, brokerURL);

            Producer producer = queue.createProducer();

            MessageGenerator messageGenerator = new MessageGenerator();
            messageGenerator.generateMessages(numberSentMessages, producer);






            Consumer consumer = queue.createConsumer();
            MessageReader messageReader = new MessageReader();
            messageReader.readMessages(consumer);

            consumer.close();
            producer.close();
            queue.close();
        } catch (Exception e) {
            LOGGER.error(e.toString(),e);
        }
    }
}

