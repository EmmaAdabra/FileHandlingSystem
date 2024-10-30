package services;

import model.Student;
import model.User;
import util.Response;

import java.util.List;

public interface IUserServices {
    boolean addUser(User user);
    List<User> getUsers();
    Response setCSVHandler(String handler);
    List<Student> getStudents();
}

