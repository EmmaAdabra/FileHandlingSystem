package infrastructure;

import model.Student;
import util.Response;

import java.util.List;

public interface ICSVHandler {
    Response<Void> writeAllStudentsToCSV(List<Student> students, String filePath);
    Response<List<Student>> readStudentFromCSV(String filePath);
    Response<Void> addStudentToCSV(Student student, String filePath);
}
