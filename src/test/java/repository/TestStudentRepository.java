package repository;


import infrastructure.DefaultCSVHandler;
import model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.Response;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestStudentRepository {
    @Mock
    DefaultCSVHandler csvHandler;

    @InjectMocks
    StudentRepository studentRepository;
    String mockedFilePath = "dummy_student.csv";

    @BeforeEach
    public void setUp(){
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

        when(csvHandler.readStudentFromCSV(anyString())).thenReturn(mockedResponse);

//        Act
        studentRepository.LoadStudentsFromCSV(mockedFilePath);

//        Assert
        verify(csvHandler, times(1)).readStudentFromCSV(anyString());
        Assertions.assertFalse(studentRepository.getStudents().isEmpty());
        Assertions.assertEquals(2, studentRepository.getStudents().size());
    }

    @Test
    public void testLoadStudentsFromEmptyCSVToPopulatesRepository(){
//        Arrange
        List<Student> students = Collections.emptyList();
        Response<List<Student>> mockedResponse = new Response<>(true, "success", students);

        when(csvHandler.readStudentFromCSV(anyString())).thenReturn(mockedResponse);

//        Act
        studentRepository.LoadStudentsFromCSV(mockedFilePath);

//        Assert
        verify(csvHandler, times(1)).readStudentFromCSV(anyString());
        Assertions.assertTrue(studentRepository.getStudents().isEmpty());
        Assertions.assertEquals(0, studentRepository.getStudents().size());
    }

    @Test
    public void testAddStudentToRepository(){
//        Arrange
        Student newStudent = new Student("AJ001", "Ajayi", 24, "Networking", 4.5);

//        Act
        studentRepository.addStudent(newStudent, mockedFilePath);

//        Assert
        Assertions.assertFalse(studentRepository.getStudents().isEmpty());
        Assertions.assertEquals(1, studentRepository.getStudents().size());
        verify(csvHandler, times(1)).addStudentToCSV(any(Student.class), anyString());
    }

    @Test
    public void testIfStudentExistInRepository(){
//        Arrange
        Student student = new Student("AJ001", "Ajayi", 24, "Networking", 4.5);
        studentRepository.addStudent(student, mockedFilePath);

//        Act & Assert
        Assertions.assertTrue(studentRepository.isStudent(student));
        verify(csvHandler, times(1)).addStudentToCSV(any(Student.class), anyString());
    }

    @Test
    public void testGetExistingStudentByID(){
//        Arrange
        Student mockedStudent = new Student("AJ001", "Ajayi", 24, "Networking", 4.5);
        studentRepository.addStudent(mockedStudent, mockedFilePath);

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
        studentRepository.addStudent(mockedStudent, mockedFilePath);

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
        when(csvHandler.writeAllStudentsToCSV(anyList(), anyString()))
                .thenReturn(new Response<>(true, "success", null));

//        Act
        studentRepository.updateStudentRecord(mockedFilePath);

//        Assert
        verify(csvHandler, times(1)).writeAllStudentsToCSV(anyList(), anyString());
    }

    @Test
    public void TestRemoveExistingStudentFromRepository(){
//        Arrange
        Student student1 = new Student("AJ003", "Ajayi Joe", 24, "Networking", 4.5);
        var student2 = new Student("MJ003", "Mercy J", 24, "Accounting", 4.5);
        studentRepository.addStudent(student1, mockedFilePath);
        studentRepository.addStudent(student2, mockedFilePath);
        int studentsSizeBeforeRemoved = studentRepository.getStudents().size();

//        Act
        boolean removed = studentRepository.removeStudent(student1.getId(), mockedFilePath);

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
        studentRepository.addStudent(student1, mockedFilePath);
        studentRepository.addStudent(student2, mockedFilePath);
        int studentsSizeBeforeRemoved = studentRepository.getStudents().size();

//        Act
        boolean removed = studentRepository.removeStudent(unknownID, mockedFilePath);

        int studentSizeAfterRemoved = studentRepository.getStudents().size();

//        Assert
        Assertions.assertEquals(2, studentsSizeBeforeRemoved);
        Assertions.assertFalse(removed);
        Assertions.assertEquals(studentsSizeBeforeRemoved, studentSizeAfterRemoved);
    }
}
