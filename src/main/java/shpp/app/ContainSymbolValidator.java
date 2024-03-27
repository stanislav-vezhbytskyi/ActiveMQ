package shpp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ContainSymbolValidator {
    private String errorMessage;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContainSymbolValidator.class);

    public boolean isValid(Object o) throws IllegalAccessException {
        Class clazz = o.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ContainSymbol.class)) {
                ContainSymbol annotation = field.getAnnotation(ContainSymbol.class);
                field.setAccessible(true);
                String str = (String) field.get(o);

                if (!str.contains(annotation.symbol())) {
                    errorMessage = annotation.message() + "'" + annotation.symbol() + "'" +
                            ", while the real name is " + "'" + str + "'";
                    LOGGER.warn(errorMessage);
                    return false;
                }
            }
        }

        return true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
