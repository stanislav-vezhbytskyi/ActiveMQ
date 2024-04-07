package shpp.app;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import net.bytebuddy.asm.Advice;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MyCSVWriterTest {
    @Test
    public void testWriteIncorrectData() throws IOException {
        CSVWriter mockCSVWriter = mock(CSVWriter.class);
        MyCSVWriter csvWriter = new MyCSVWriter();

        List<POJO> incorrectDataList = Arrays.asList(
                new POJO("Name1", "", 555, LocalDate.MAX),
                new POJO("Name1", "", 555, LocalDate.MAX)
        );

        List<String> errorList = Arrays.asList("Error1", "Error2");

        csvWriter.writeIncorrectData(incorrectDataList, errorList, mockCSVWriter);

        verify(mockCSVWriter, times(2)).writeNext(any());
    }
}
