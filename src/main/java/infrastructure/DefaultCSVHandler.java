package infrastructure;

import model.Student;
import util.CSVHelpers;
import util.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultCSVHandler implements ICSVHandler {
    private final String CSV_HEADER = "ID,NAME,AGE,COURSE,GPA";

    @Override
    public Response<Void> writeAllStudentsToCSV(List<Student> students, String filePath) {
        String errorMsg = null;

        try (var csvWriter = new BufferedWriter(new FileWriter(filePath))) {
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
            errorMsg = e.getMessage();
        } catch (Exception e){
            errorMsg = e.getMessage();
            GlobalErrorHandler.handleException(e);
        }

        return errorMsg != null
            ? new Response<>(false, errorMsg, null)
            : new Response<>(true, "success", null);
    }

    @Override
    public Response<List<Student>> readStudentFromCSV(String filePath) {
        List<Student> students = new ArrayList<>();
        boolean firstRow = true;
        String errorMsg = null;

        if(!CSVHelpers.fileExist(filePath)) {
            try {
                CSVHelpers.createFile(filePath);
                return new Response<>(true, filePath
                        + " file have been created successfully", new ArrayList<>());

            } catch (IOException e) {
                errorMsg = e.getMessage();
            } catch (Exception e){
                errorMsg = "Unexpected error occur: " + e.getMessage();
                GlobalErrorHandler.handleException(e);
            }
        }

        if(CSVHelpers.isEmpty(filePath)){
            return new Response<>(true, filePath
                    + " loaded successfully (no student records found)", new ArrayList<>());
        }

        try (var csvReader = new BufferedReader(new FileReader(filePath))) {
            String studentRecord;

            while ((studentRecord = csvReader.readLine()) != null) {
//                Todo: write logic for case when the first row is student data not header
                if (firstRow) {
                    firstRow = false;
                } else {
                    Student student = csvToStudent(studentRecord);
                    if (student != null) {
                        students.add(student);
                    }
                }
            }
        } catch (IOException e) {
            errorMsg = e.getMessage();
        } catch (Exception e){
            errorMsg = e.getMessage();
            GlobalErrorHandler.handleException(e);
        }

        return errorMsg != null
            ? new Response<>(false, errorMsg, null)
            : new Response<>(true, filePath + " loaded successfully", students);
    }

    @Override
    public Response<Void> addStudentToCSV(Student student, String filePath) {
        String errorMsg;
        boolean fileExist = CSVHelpers.fileExist(filePath);

        try (var csvWriter = new BufferedWriter(new FileWriter(filePath, true))) {

            if (!fileExist || CSVHelpers.isEmpty(filePath)) {
                csvWriter.write(CSV_HEADER);
                csvWriter.newLine();
            } else {
                csvWriter.newLine();
            }

            String studentRecord = studentToCSVRow(student);
            csvWriter.write(studentRecord);

            return new Response<>(true, "success", null);
        } catch (IOException e) {
            errorMsg = e.getMessage();
        } catch (Exception e){
            errorMsg = e.getMessage();
            GlobalErrorHandler.handleException(e);
        }

        return new Response<>(false, errorMsg, null);
    }

    private String studentToCSVRow(Student student) {
        final String DELIMITER = ",";

        return student.getId() + DELIMITER + student.getName() + DELIMITER + student.getAge()
                + DELIMITER + student.getCourse() + DELIMITER + student.getGpa();
    }

    private Student csvToStudent(String studentDetails) {
//        Todo: should throw validation error
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
