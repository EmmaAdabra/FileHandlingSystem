package infrastructure;

import model.Student;
import org.apache.commons.io.FileUtils;
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
        File tempFile = Files.createTempFile("test_tudent", ".csv").toFile();
        FileUtils.forceDeleteOnExit(tempFile);

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
        Assertions.assertEquals("Emmanuel", students.get(1).getName());
    }

    @Test
    public void testWriteAllStudentsToCSV() throws IOException{
//        Arrange
        File tempFile = Files.createTempFile("test_student", ".csv").toFile();
//        FileUtils.forceDeleteOnExit(tempFile);

        List<Student> students = List.of(
                new Student("AJ001", "Ajayi", 24, "Neworking", 4.5),
                new Student("EA002", "Emmanuel", 24, "programming", 4.5)
        );

//        Act
        Response<Void> response = csvHandler.writeAllStudentsToCSV(students, tempFile.getAbsolutePath());

//        Assert
        Assertions.assertTrue(response.status);
        Assertions.assertEquals("success", response.message);
    }

    @Test
    public void testAddStudent() throws IOException{
//        Arrange
        File tempFile = Files.createTempFile("test_student", ".csv").toFile();
        try(MockedStatic<CSVHelpers> mockCSVHandler = mockStatic(CSVHelpers.class)){
            mockCSVHandler.when(() -> CSVHelpers.fileExist(anyString())).thenReturn(true);
            mockCSVHandler.when(() -> CSVHelpers.isEmpty(anyString())).thenReturn(true);
        }

        var mockStudent = new Student("EA002", "Emmanuel Adabra", 24, "programming", 4.5);

//        Act
        var response = csvHandler.addStudentToCSV(mockStudent, tempFile.getAbsolutePath());

//        Assert
        Assertions.assertTrue(response.status);
        Assertions.assertEquals("success", response.message);
    }
}
