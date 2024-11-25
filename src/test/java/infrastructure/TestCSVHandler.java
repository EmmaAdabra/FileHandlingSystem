package infrastructure;

import model.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import util.CSVHelpers;
import util.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class TestCSVHandler {
    ICSVHandler csvHandler = new DefaultCSVHandler();

    @Test
     public void testReadStudentFromCSV() throws IOException {
//        Arrange
        File tempFile = Files.createTempFile("testStudent", ".csv").toFile();
        tempFile.deleteOnExit();

        try(FileWriter writer = new FileWriter(tempFile)) {
            writer.write("ID,Name,Age,Department,GPA\n");
            writer.write("AJ001,Ajayi,24,Networking,4.5\n");
            writer.write("EA002,Emmanuel,22,Programming,3.8\n");
        }

        try(MockedStatic<CSVHelpers> csvHelperMock = mockStatic(CSVHelpers.class)){
            csvHelperMock.when(() -> CSVHelpers.fileExist(anyString())).thenReturn(true);
            csvHelperMock.when(() -> CSVHelpers.isEmpty(anyString())).thenReturn(false);
        }

//        Act
        Response<List<Student>> response = csvHandler.readStudentFromCSV(tempFile.getAbsolutePath());
        var students = response.obj;

        Assertions.assertTrue(response.status);
        Assertions.assertEquals(2, students.size());
        Assertions.assertEquals("AJ001", students.get(0).getId());
    }
}
