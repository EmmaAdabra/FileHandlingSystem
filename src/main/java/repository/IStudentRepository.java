package repository;

import infrastructure.ICSVHandler;
import model.Student;
import util.Response;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {
    Response<Void> LoadStudentsFromCSV();
    List<Student> getStudents();
    void addStudent(Student student);
    boolean isStudent(Student student);
    Optional<Student> getStudentByID(String id);
    Response<Void> updateStudentRecord();
    List<Student> getStudentByName(String name);
    boolean removeStudent(String id);
    void setCSVHandler(ICSVHandler csvHandler);
}

