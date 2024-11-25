package repository;


import infrastructure.DefaultCSVHandler;
import model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.Response;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestStudentRepository {
    @Mock
    DefaultCSVHandler csvHandler;
    IStudentRepository studentRepository;

    @BeforeEach
    public void setUp(){
        studentRepository = new StudentRepository();
        studentRepository.setCSVHandler(csvHandler);
    }

    @Test
    public void testLoadStudentsFromCSVPopulatesRepository(){
//        Arrange
        List<Student> students = List.of(
                new Student("AJ001", "Ajayi", 24, "Neworking", 4.5),
                new Student("EA002", "Emmanuel", 24, "programming", 4.5)
                );
        Response<List<Student>> mockedResponse = new Response<>(true, "success", students);

        when(csvHandler.readStudentFromCSV()).thenReturn(mockedResponse);

//        Act
        studentRepository.LoadStudentsFromCSV();

//        Assert
        verify(csvHandler, times(1)).readStudentFromCSV();
        Assertions.assertFalse(studentRepository.getStudents().isEmpty());
        Assertions.assertEquals(2, studentRepository.getStudents().size());
    }

    @Test
    public void testLoadStudentsFromEmptyCSVToPopulatesRepository(){
//        Arrange
        List<Student> students = Collections.emptyList();
        Response<List<Student>> mockedResponse = new Response<>(true, "success", students);

        when(csvHandler.readStudentFromCSV()).thenReturn(mockedResponse);

//        Act
        studentRepository.LoadStudentsFromCSV();

//        Assert
        verify(csvHandler, times(1)).readStudentFromCSV();
        Assertions.assertTrue(studentRepository.getStudents().isEmpty());
        Assertions.assertEquals(0, studentRepository.getStudents().size());
    }

    @Test
    public void testAddStudentToRepository(){
//        Arrange
        Student newStudent = new Student("AJ001", "Ajayi", 24, "Networking", 4.5);
        when(csvHandler.addStudentToCSV(newStudent)).thenReturn(any(Response.class));

//      Act
        studentRepository.addStudent(newStudent);

//        Assert
        Assertions.assertFalse(studentRepository.getStudents().isEmpty());
        Assertions.assertEquals(1, studentRepository.getStudents().size());
        verify(csvHandler, times(1)).addStudentToCSV(newStudent);
    }

    @Test
    public void testIfStudentExistInRepository(){
//        Arrange
        Student student = new Student("AJ001", "Ajayi", 24, "Networking", 4.5);
        studentRepository.addStudent(student);

//        Act & Assert
        Assertions.assertTrue(studentRepository.isStudent(student));
    }

    @Test
    public void testGetExistingStudentByID(){
        //        Arrange
        Student mockedStudent = new Student("AJ001", "Ajayi", 24, "Networking", 4.5);
        studentRepository.addStudent(mockedStudent);

//        Act
        Optional<Student> student = studentRepository.getStudentByID(mockedStudent.getId());

//        Assert
        Assertions.assertEquals(mockedStudent, student.orElse(null));
        Assertions.assertFalse(student.isEmpty());
    }

    @Test
    public void testNonExistingStudentByID(){
        //        Arrange
        Student mockedStudent = new Student("AJ003", "Mercy", 24, "Accounting", 4.5);

//        Act
        Optional<Student> student = studentRepository.getStudentByID(mockedStudent.getId());

//        Assert
        Assertions.assertFalse(student.isPresent());
    }

    @Test
    public void testGetExistingStudentsByName(){
        //        Arrange
        Student mockedStudent = new Student("AJ001", "Ajayi", 24, "Networking", 4.5);
        studentRepository.addStudent(mockedStudent);

//        Act
        List<Student> students = studentRepository.getStudentByName(mockedStudent.getName());

//        Assert
        Assertions.assertEquals(1, students.size());
        Assertions.assertFalse(students.isEmpty());
    }

    @Test
    public void testNonExistingStudentByName(){
        //        Arrange
        Student mockedStudent = new Student("AJ003", "Mercy", 24, "Accounting", 4.5);

//        Act
        List<Student> students = studentRepository.getStudentByName(mockedStudent.getName());

//        Assert
        Assertions.assertTrue(students.isEmpty());
    }

    @Test
    public void testUpdateStudentRecord(){
//        Arrange
        when(csvHandler.writeAllStudentsToCSV(anyList()))
                .thenReturn(new Response<>(true, "success", null));

//        Act
        studentRepository.updateStudentRecord();

//        Assert
        verify(csvHandler, times(1)).writeAllStudentsToCSV(anyList());
    }

    @Test
    public void TestRemoveExistingStudentFromRepository(){
//        Arrange
        Student student1 = new Student("AJ003", "Ajayi Joe", 24, "Networking", 4.5);
        var student2 = new Student("MJ003", "Mercy J", 24, "Accounting", 4.5);
        studentRepository.addStudent(student1);
        studentRepository.addStudent(student2);
        int studentsSizeBeforeRemoved = studentRepository.getStudents().size();

//        Act
        boolean removed = studentRepository.removeStudent(student1.getId());

//        Assert
        Assertions.assertEquals(2, studentsSizeBeforeRemoved);
        Assertions.assertTrue(removed);
        Assertions.assertEquals(1, studentRepository.getStudents().size());
    }

    @Test
    public void TestRemoveNonExistingStudentFromRepository(){
//        Arrange
        String unknownID = "EE11";
        Student student1 = new Student("AJ003", "Ajayi Joe", 24, "Networking", 4.5);
        var student2 = new Student("MJ003", "Mercy J", 24, "Accounting", 4.5);
        studentRepository.addStudent(student1);
        studentRepository.addStudent(student2);
        int studentsSizeBeforeRemoved = studentRepository.getStudents().size();

//        Act
        boolean removed = studentRepository.removeStudent(unknownID);

        int studentSizeAfterRemoved = studentRepository.getStudents().size();

//        Assert
        Assertions.assertEquals(2, studentsSizeBeforeRemoved);
        Assertions.assertFalse(removed);
        Assertions.assertEquals(studentsSizeBeforeRemoved, studentSizeAfterRemoved);
    }
}
