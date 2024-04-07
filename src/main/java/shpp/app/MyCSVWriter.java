package shpp.app;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.List;

public class MyCSVWriter {
    public void writeIncorrectData(List<POJO> incorrectDataList, List<String> errorList, CSVWriter fileWriter) throws IOException {
        for (int i = 0; i < incorrectDataList.size() && i < errorList.size(); i++) {
            String[] tempRow = {incorrectDataList.get(i).getName(),
                    String.valueOf(incorrectDataList.get(i).getCount()),
                    errorList.get(i)};
            fileWriter.writeNext(tempRow);
        }
        fileWriter.close();
    }
}
