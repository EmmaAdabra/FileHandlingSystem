package services;

import infrastructure.DefaultCSVHandler;
import model.Student;
import model.User;
import repository.IStudentRepository;
import repository.IUserRepository;
import repository.StudentRepository;
import util.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServices implements IUserServices {
    IStudentRepository studentRepository;
    IUserRepository userRepository;

    public UserServices(IUserRepository userRepository) {
        this.userRepository = userRepository;
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
    public Response setCSVHandler(String handler) {
        final String MAIN = "main";
        Response response = null;

        if (MAIN.equals(handler)) {
            this.studentRepository = new StudentRepository(new DefaultCSVHandler());
            response = studentRepository.loadStudentCSV();
        }

        return response;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.getStudents();
    }

    @Override
    public boolean addStudent(Student newStudent) {
        if(!studentRepository.isStudent(newStudent)) {
            studentRepository.addStudent(newStudent);

            return true;
        }

        return false;
    }

    @Override
    public Optional<Student> getStudentByID(String id) {
        return studentRepository.getStudentByID(id);
    }

    @Override
    public Response updateStudentRecord() {
        return studentRepository.updateStudentRecord();
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
    public boolean deleteStudent(String studentID) {
        return studentRepository.removeStudent(studentID);
    }

}

