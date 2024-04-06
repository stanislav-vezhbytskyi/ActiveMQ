package shpp.app;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class POJOAndExceptionsCSVWriter {
    Logger LOGGER = LoggerFactory.getLogger(POJOAndExceptionsCSVWriter.class);

    public void writePOJOAndExceptionsList(List<POJO> pojoList, List<String> exceptions, CSVWriter CSVWriter)
            throws IOException {
        try {
            for (int i = 0; i < pojoList.size() && i < exceptions.size(); i++) {
                String[] tempRow = {pojoList.get(i).getName(), String.valueOf(pojoList.get(i).getCount()),
                        exceptions.get(i)};
                CSVWriter.writeNext(tempRow);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        LOGGER.info("THE FILE HAS BEEN WRITTEN SUCCESSFULLY");
    }
}
