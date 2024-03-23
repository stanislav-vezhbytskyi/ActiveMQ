package shpp.app;

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
    private final Logger LOGGER = LoggerFactory.getLogger(MessageReader.class);

    public void readMessages(Consumer consumer) throws JMSException, IllegalAccessException {
        List<POJO> listWithCorrectPojo = new LinkedList<>();
        List<POJO> listWithIncorrectPojo = new LinkedList<>();
        List<String> listWithErrors = new LinkedList<>();

        POJO pojo;
        while ((pojo = consumer.receive()) != null) {
            IsContainSymbolValidator isContainSymbolValidator = new IsContainSymbolValidator();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<POJO>> violations = validator.validate(pojo);

            LOGGER.info("{}", pojo);

            if (violations.isEmpty() && isContainSymbolValidator.isValid(pojo)) {
                listWithCorrectPojo.add(pojo);
            } else {
                String errorMessage = "";
                if(!violations.isEmpty()){
                    errorMessage = violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining())+"; ";
                }
                if(!isContainSymbolValidator.isValid(pojo)){
                    errorMessage+=isContainSymbolValidator.getErrorMessage();
                }
                listWithErrors.add(errorMessage);
                listWithIncorrectPojo.add(pojo);
            }
        }
        MyCSVWriter csvWriter = new MyCSVWriter();

        try {
            csvWriter.writePOJOList(listWithCorrectPojo);
            csvWriter.writePOJOAndExceptionsList(listWithIncorrectPojo,listWithErrors);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);

        } catch (CsvRequiredFieldEmptyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }


    }
}
