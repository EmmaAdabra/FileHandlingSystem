package infrastructure;

import model.Student;
import util.Response;

import java.util.List;

public interface ICSVHandler {
    Response writeAllStudentsToCSV(List<Student> students);
    Response readStudentFromCSV();
    Response addStudentToCSV(Student student);
}
