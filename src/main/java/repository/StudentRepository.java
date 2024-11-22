package repository;

import infrastructure.ICSVHandler;
import model.Student;
import util.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StudentRepository implements IStudentRepository  {
    private  ICSVHandler csvHandler;

    private final List<Student> students = new ArrayList<>();

    @Override
    public Response<Object> LoadStudentsFromCSV() {
        Response<Object> response = csvHandler.readStudentFromCSV();

        if(response.status) {
//            Note: modify logic later
            List<Student> studentRecords = (List<Student>) response.obj;
            students.addAll(studentRecords);
            return new Response<Object>(true, response.message, null);
        }
        return response;
    }

    @Override
    public List<Student> getStudents() {
        return students;
    }

    @Override
//    Note: Modify, addStudent should return response a
    public void addStudent(Student student) {
        students.add(student);
        csvHandler.addStudentToCSV(student);
    }

    @Override
    public boolean isStudent(Student student) {
        return students.contains(student);
    }

    @Override
    public Optional<Student> getStudentByID(String id) {
        return students.stream().filter(student -> student.getId().equals(id)).findFirst();
    }

    @Override
    public Response<Object> updateStudentRecord() {
        return csvHandler.writeAllStudentsToCSV(students);
    }

    @Override
    public List<Student> getStudentByName(String name) {
        return students.stream().filter(student -> student.getName().equalsIgnoreCase(name)).toList();
    }

    @Override
    public boolean removeStudent(String id) {
        var student = getStudentByID(id);

        if(student.isEmpty()){
            return false;
        }

        boolean deleted = students.removeIf(stud -> stud.getId().equals(id));

        if(deleted) {
            updateStudentRecord();
            return true;
        }

        return false;
    }

    public void setCSVHandler(ICSVHandler csvHandler) {
        this.csvHandler = csvHandler;
    }
}
