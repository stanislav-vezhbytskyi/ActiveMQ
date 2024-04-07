package shpp.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

public class MessageHandler {
    private final String JSON_FORM = "{\"error\":[%s]}";
    private final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);
    private final Validator validator;
    private final ContainSymbolValidator containSymbolValidator;
    private ObjectMapper objectMapper;
    private List<POJO> listWithCorrectPojo;
    private List<POJO> listWithIncorrectPojo;

    private List<String> listWithErrors;

    public MessageHandler() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.containSymbolValidator = new ContainSymbolValidator();
        this.objectMapper = new ObjectMapper().findAndRegisterModules();
        this.listWithCorrectPojo = new LinkedList<>();
        this.listWithIncorrectPojo = new LinkedList<>();
        this.listWithErrors = new LinkedList<>();
    }

    public void readMessages(Consumer consumer, String poisonPillMessage) throws JMSException, IllegalAccessException, JsonProcessingException {
        String tempJson = consumer.receive();
        while (tempJson != null && !tempJson.equals(poisonPillMessage)) {
            POJO pojo = parseJsonToPOJO(tempJson);
            validateAndSeparate(pojo);
            LOGGER.info(tempJson);
            tempJson = consumer.receive();
        }
    }

    private POJO parseJsonToPOJO(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, POJO.class);
    }

    private void validateAndSeparate(POJO pojo) throws IllegalAccessException {
        Set<ConstraintViolation<POJO>> violations = validator.validate(pojo);
        if (violations.isEmpty() && containSymbolValidator.isValid(pojo)) {
            listWithCorrectPojo.add(pojo);
        } else {
            String errorMessage = buildErrorMessage(violations, pojo);
            listWithErrors.add(String.format(JSON_FORM, errorMessage));
            listWithIncorrectPojo.add(pojo);
        }
    }

    private String buildErrorMessage(Set<ConstraintViolation<POJO>> violations, POJO pojo) throws IllegalAccessException {
        String errorMessage = "";
        if (!violations.isEmpty()) {
            errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining()) + "; ";
        }
        if (!containSymbolValidator.isValid(pojo)) {
            errorMessage += containSymbolValidator.getErrorMessage();
        }
        return errorMessage;
    }

    public List<POJO> getListWithCorrectPojo() {
        return listWithCorrectPojo;
    }

    public List<POJO> getListWithIncorrectPojo() {
        return listWithIncorrectPojo;
    }

    public List<String> getListWithErrors() {
        return listWithErrors;
    }
}
