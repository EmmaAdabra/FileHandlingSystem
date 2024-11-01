package repository;

import infrastructure.ICSVHandler;
import model.Student;
import util.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements IStudentRepository  {
    private final ICSVHandler csvHandler;

    private final List<Student> students = new ArrayList<>();

    public StudentRepository(ICSVHandler csvHandler) {
        this.csvHandler = csvHandler;
    }

    @Override
    public Response loadStudentCSV() {
        Response response = csvHandler.readStudentFromCSV();

        if(response.status) {
//            Note: modify logic later
            List<Student> studentRecords = (List<Student>) response.obj;
            students.addAll(studentRecords);
            return new Response(true, response.message, null);
        }
        return response;
    }

    @Override
    public List<Student> getStudents() {
        return students;
    }

    @Override
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
    public Response updateStudentRecord() {
        return csvHandler.writeAllStudentsToCSV(students);
    }

    @Override
    public List<Student> getStudentByName(String name) {
        return students.stream().filter(student -> student.getName().equalsIgnoreCase(name)).toList();
    }
}
