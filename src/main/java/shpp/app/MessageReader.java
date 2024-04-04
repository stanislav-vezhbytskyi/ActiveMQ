package shpp.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MessageReader {
    private final String JSON_FORM = "{\"error\":[%s]}";
    private final Logger LOGGER = LoggerFactory.getLogger(MessageReader.class);

    public void readMessages(Consumer consumer, String poisonPillMessage) throws JMSException, IllegalAccessException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        List<POJO> listWithCorrectPojo = new LinkedList<>();
        List<POJO> listWithIncorrectPojo = new LinkedList<>();
        List<String> listWithErrors = new LinkedList<>();

        ContainSymbolValidator isContainSymbolValidator = new ContainSymbolValidator();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        POJO pojo;
        String tempJson = consumer.receive();
        while (!tempJson.equals(poisonPillMessage)) {
            pojo = objectMapper.readValue(tempJson, POJO.class);

            Set<ConstraintViolation<POJO>> violations = validator.validate(pojo);

            LOGGER.debug("{}", pojo);

            if (violations.isEmpty() && isContainSymbolValidator.isValid(pojo)) {
                listWithCorrectPojo.add(pojo);
            } else {
                String errorMessage = "";
                if (!violations.isEmpty()) {
                    errorMessage = violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining()) + "; ";
                }
                if (!isContainSymbolValidator.isValid(pojo)) {
                    errorMessage += isContainSymbolValidator.getErrorMessage();
                }
                listWithErrors.add(String.format(JSON_FORM, errorMessage));
                listWithIncorrectPojo.add(pojo);
            }
            tempJson = consumer.receive();
        }
        MyCSVWriter csvWriter = new MyCSVWriter();

        try {
            csvWriter.writePOJOList(listWithCorrectPojo,"csvWithCorrectData");
            csvWriter.writePOJOAndExceptionsList(listWithIncorrectPojo, listWithErrors,"csvWithIncorrectData");
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }
}
