package shpp.app;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class MyCSVWriter {
    public void writePOJOList(List<POJO> pojoList) throws IOException,
            CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = new FileWriter("mycsvfile.csv");
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(pojoList);
        writer.close();
        System.out.println("THE FILE WAS WRITTEN SUCCESSFULLY");
    }

    public void writePOJOAndExceptionsList(List<POJO> pojoList, List<String> exceptions) throws IOException{

        CSVWriter writer = new CSVWriter(new FileWriter("mycsvFileWithincorrectData.csv"));
        for (int i = 0; i < pojoList.size() && i < exceptions.size(); i++) {
            String[] tempRow = {pojoList.get(i).name, String.valueOf(pojoList.get(i).count),exceptions.get(i)};
            writer.writeNext(tempRow);
        }

        System.out.println("THE FILE WAS WRITTEN SUCCESSFULLY");
    }
}
