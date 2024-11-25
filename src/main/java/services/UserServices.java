package services;

import infrastructure.ICSVHandler;
import model.Student;
import model.User;
import repository.IStudentRepository;
import repository.IUserRepository;
import repository.StudentRepository;
import util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServices implements IUserServices {
    IStudentRepository studentRepository;
    IUserRepository userRepository;

    public UserServices(IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.studentRepository = new StudentRepository();
    }

    @Override
    public boolean addUser(User newUser) {
        var user = userRepository.getUser(newUser.getEmail());

        if(user == null) {
            userRepository.addUser(newUser);
            return true;
        }

        return false;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public boolean setCSVHandler(CSVHandlerType handlerType) {
        ICSVHandler csvHandler = CSVHandlerFactory.createCSVHandler(handlerType);

        if (csvHandler == null) {
            return false;
        }
        studentRepository.setCSVHandler(csvHandler);

        return true;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.getStudents();
    }

    @Override
//    Todo: Modify, addStudent should return response
    public boolean addStudent(Student newStudent, String filePath) {
        if(!studentRepository.isStudent(newStudent)) {
            studentRepository.addStudent(newStudent, filePath);

            return true;
        }

        return false;
    }

    @Override
    public Optional<Student> getStudentByID(String id) {
        return studentRepository.getStudentByID(id);
    }

    @Override
    public Response<Void> updateStudentRecord(String filePath) {
        return studentRepository.updateStudentRecord(filePath);
    }

    @Override
    public Map<Integer, Student> getStudentsByName(String name) {
        int count = 1;
        Map<Integer, Student> studentRecord = new HashMap<>();

        for(Student student : studentRepository.getStudentByName(name)){
            studentRecord.put(count++, student);
        }
        return studentRecord;
    }

    @Override
    public boolean deleteStudent(String studentID, String filePath) {
        return studentRepository.removeStudent(studentID, filePath);
    }

    @Override
    public Response<Void> loadStudentFromCSV(String filePath) {
        return studentRepository.LoadStudentsFromCSV(filePath);
    }
}

