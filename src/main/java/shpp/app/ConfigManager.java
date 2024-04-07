package shpp.app;

import java.util.Properties;

public class ConfigManager {
    private int numberSentMessages = 1000;
    private static final String PROPERTY_FILE_NAME = "app.properties";
    private  String clientId;
    private  String queueName;
    private  String poisonPillMessage = "THE END OF THE QUEUE";
    private String brokerURL;
    private long poisonPill;
    public void readConfiguration(String propertyFileName){
        if (System.getProperty("N") != null) {
            numberSentMessages = Integer.parseInt(System.getProperty("N"));
        }

        PropertyReader propertyReader = new PropertyReader();
        Properties properties = propertyReader.readProperties(PROPERTY_FILE_NAME);
        if (properties != null && !properties.isEmpty()) {
            queueName = properties.getProperty("queueName");
            clientId = properties.getProperty("clientID");
            brokerURL = properties.getProperty("brokerURL");
            poisonPill = Long.valueOf(properties.getProperty("poisonPill"));
        }
    }
    public int getNumberSentMessages() {
        return numberSentMessages;
    }

    public String getClientId() {
        return clientId;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getPoisonPillMessage() {
        return poisonPillMessage;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public long getPoisonPill() {
        return poisonPill;
    }

}
