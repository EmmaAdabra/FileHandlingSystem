package services;

import model.Student;
import model.User;
import util.Response;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface IUserServices {
    boolean addUser(User user);
    List<User> getUsers();
    Response<Objects> setCSVHandler(CSVHandlerType handlerType);
    List<Student> getStudents();
    boolean addStudent(Student newStudent);
    Optional<Student> getStudentByID(String id);
    Response<Objects> updateStudentRecord();
    Map<Integer, Student> getStudentsByName(String name);
    boolean deleteStudent(String studentID);
}

