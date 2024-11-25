package services;

import model.Student;
import model.User;
import util.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserServices {
    boolean addUser(User user);
    List<User> getUsers();
    boolean setCSVHandler(CSVHandlerType handlerType);
    List<Student> getStudents();
    boolean addStudent(Student newStudent, String filePath);
    Optional<Student> getStudentByID(String id);
    Response<Void> updateStudentRecord(String filePath);
    Map<Integer, Student> getStudentsByName(String name);
    boolean deleteStudent(String studentID, String filePath);
    Response<Void> loadStudentFromCSV(String filePath);
}

