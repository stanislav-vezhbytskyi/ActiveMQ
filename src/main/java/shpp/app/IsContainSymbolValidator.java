package shpp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class IsContainSymbolValidator  {
    private String errorMessage;
    private static final Logger LOGGER = LoggerFactory.getLogger(IsContainSymbolValidator.class);
    public boolean isValid(Object o) throws IllegalAccessException {
        Class clazz = o.getClass();
        for(Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(IsContainSymbol.class)){
                IsContainSymbol annotation = field.getAnnotation(IsContainSymbol.class);
                field.setAccessible(true);
                String str = (String) field.get(o);

                if(!str.contains(annotation.symbol())){
                    errorMessage = annotation.message() + "'"+annotation.symbol()+"'"+", while the real name is "+str;
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
