package shpp.app;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class MyCSVWriter {
    Logger LOGGER = LoggerFactory.getLogger(MyCSVWriter.class);

    public void writePOJOList(List<POJO> pojoList, String fileName) throws IOException,
            CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = new FileWriter(fileName);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(pojoList);
        writer.close();
        LOGGER.info("THE FILE HAS BEEN WRITTEN SUCCESSFULLY");
    }

    public void writePOJOAndExceptionsList(List<POJO> pojoList, List<String> exceptions, String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName); CSVWriter writer = new CSVWriter(fileWriter)) {
            for (int i = 0; i < pojoList.size() && i < exceptions.size(); i++) {
                String[] tempRow = {pojoList.get(i).getName(), String.valueOf(pojoList.get(i).getCount()), exceptions.get(i)};
                writer.writeNext(tempRow);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        LOGGER.info("THE FILE HAS BEEN WRITTEN SUCCESSFULLY");
    }
}
