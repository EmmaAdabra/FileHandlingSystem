package repository;

import infrastructure.ICSVHandler;
import model.Student;
import util.Response;

import java.util.List;

public interface IStudentRepository {
    Response loadStudentCSV();
    List<Student> getStudents();
}
