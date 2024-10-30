package repository;

import infrastructure.ICSVHandler;
import model.Student;
import util.Response;

import java.util.ArrayList;
import java.util.List;

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
            return new Response(true, "success", null);
        }
        return response;
    }

    @Override
    public List<Student> getStudents() {
        return students;
    }
}
