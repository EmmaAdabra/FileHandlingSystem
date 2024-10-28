package infrastructure;

import model.Student;
import util.CSVHelpers;
import util.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultCSVHandler implements ICSVHandler {
    private final String FILE_PATH = "students.csv";
    private final String CSV_HEADER = "ID,NAME,AGE,COURSE,GPA";

    @Override
    public Response writeAllStudentsToCSV(List<Student> students) {
        try (var csvWriter = new BufferedWriter(new FileWriter(FILE_PATH))) {
            csvWriter.write(CSV_HEADER);
            csvWriter.newLine();
            String csvRow;

            for (int i = 0; i < students.size(); i++) {
                csvRow = studentToCSVRow(students.get(i));
                csvWriter.write(csvRow);

                if (i != (students.size() - 1)) {
                    csvWriter.newLine();
                }
            }

        } catch (IOException e) {
            return new Response(false, e.getMessage(), null);
        }

        return new Response(true, "success", null);
    }

    @Override
    public Response readStudentFromCSV() {
        List<Student> students = new ArrayList<>();
        boolean firstRow = true;

        try (var csvReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String studentRecord;

            while ((studentRecord = csvReader.readLine()) != null) {
                if (firstRow) {
                    firstRow = false;
                } else {
                    Student student = csvToStudent(studentRecord);
                    if (student != null) {
                        students.add(student);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return new Response(false, e.getMessage(), null);
        } catch (IOException e) {
            return new Response(false, e.getMessage(), null);
        }


        return new Response(true, "success", students);
    }

    @Override
    public Response addStudentToCSV(Student student) {
        boolean fileExist = CSVHelpers.fileExist(FILE_PATH);

        try (var csvWriter = new BufferedWriter(new FileWriter(FILE_PATH, true))) {

            if (!fileExist || CSVHelpers.isEmpty(FILE_PATH)) {
                csvWriter.write(CSV_HEADER);
                csvWriter.newLine();
            } else {
                csvWriter.newLine();
            }

            String studentRecord = studentToCSVRow(student);
            csvWriter.write(studentRecord);

            return new Response(true, "success", null);
        } catch (IOException e) {
            return new Response(false, e.getMessage(), null);
        }
    }

    private String studentToCSVRow(Student student) {
        final String DELIMITER = ",";

        return student.getId() + DELIMITER + student.getName() + DELIMITER + student.getAge()
                + DELIMITER + student.getCourse() + DELIMITER + student.getGpa();
    }

    private Student csvToStudent(String studentDetails) {
        if (!studentDetails.trim().isEmpty()) {
            String[] studentData = studentDetails.split(",");
            String studentID = studentData[0];
            String name = studentData[1];
            int age = Integer.parseInt(studentData[2]);
            String course = studentData[3];
            double gpa = Double.parseDouble(studentData[4]);

            return new Student(studentID, name, age, course, gpa);
        }

        return null;
    }
}
