package repository;

import model.Student;
import util.Response;

import java.util.List;

public interface IStudentRepository {
    Response loadStudentCSV();
    List<Student> getStudents();
    void addStudent(Student student);
    boolean isStudent(Student student);
}

