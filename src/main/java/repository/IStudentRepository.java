package repository;

import infrastructure.ICSVHandler;
import model.Student;
import util.Response;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {
    Response<Void> LoadStudentsFromCSV(String filePath);
    List<Student> getStudents();

    void addStudent(Student student, String filePath);
    boolean isStudent(Student student);
    Optional<Student> getStudentByID(String id);
    Response<Void> updateStudentRecord(String filePath);
    List<Student> getStudentByName(String name);
    boolean removeStudent(String id, String filePath);
    void setCSVHandler(ICSVHandler csvHandler);
}

