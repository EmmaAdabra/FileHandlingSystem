package infrastructure;

import model.Student;
import util.Response;

import java.util.List;

public interface ICSVHandler {
    Response<Void> writeAllStudentsToCSV(List<Student> students);
    Response<List<Student>> readStudentFromCSV();
    Response<Void> addStudentToCSV(Student student);
}
