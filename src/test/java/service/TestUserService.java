package service;

import infrastructure.DefaultCSVHandler;
import infrastructure.ICSVHandler;
import model.Student;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.StudentRepository;
import repository.UserRepository;
import services.CSVHandlerType;
import services.UserServices;
import util.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUserService {
    @Mock
    private DefaultCSVHandler csvHandler;
    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private UserServices userServices;
    @BeforeEach
    public void setUp(){
       studentRepository.setCSVHandler(csvHandler);
       MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addNewUserTest(){
    //       Arrange
       User newUser = new User("Ajayi j", "aj@gmail.com", "1234");
       when(userRepository.getUser(newUser.getEmail())).thenReturn(null);
       doNothing().when(userRepository).addUser(newUser);

    //       Act
       boolean added = userServices.addUser(newUser);

    //       Assert
       verify(userRepository, times(1)).getUser(newUser.getEmail());
       verify(userRepository, times(1)).addUser(newUser);
       Assertions.assertTrue(added);
    }

    @Test
    public void addExistingUserTest(){
    //       Arrange
        User user = new User("Ajayi j", "aj@gmail.com", "1234");
        when(userRepository.getUser(user.getEmail())).thenReturn(user);

    //       Act
        boolean added = userServices.addUser(user);

    //       Assert
        verify(userRepository, times(1)).getUser(user.getEmail());
        verify(userRepository, times(0)).addUser(user);
        Assertions.assertFalse(added);
    }

    @Test
    public void testGetUsers(){
    //       Arrange
        List<User> mockUsersList = new ArrayList<>(
                List.of(new User("Ajayi j", "aj@gmail.com", "1234"),
                        new User("Emma A", "emma@gmail.com", "1234"))
        );

        when(userRepository.getAllUsers()).thenReturn(mockUsersList);

    //      Act
        List<User> users = userServices.getUsers();

    //       Assert
        verify(userRepository, times(1)).getAllUsers();
        Assertions.assertEquals(mockUsersList,users);
    }

    @Test
    public void testSetCSVHandler_WithMainHandler() {
        // Arrange
        CSVHandlerType mockDefaultType = CSVHandlerType.DEFAULT;
        Response<Void> mockResponse = new Response<>(true, "Students loaded successfully", null);
        doNothing().when(studentRepository).setCSVHandler(any(ICSVHandler.class));
        when(studentRepository.LoadStudentsFromCSV()).thenReturn(mockResponse);

        // Act
        Response<Void> response = userServices.setCSVHandler(mockDefaultType);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.status);
        Assertions.assertEquals("Students loaded successfully", response.message);
    }

    @Test
    public void testGetStudent(){
    //       Arrange
        List<Student> mockedStudentList = List.of(
                new Student("AJ001", "Ajayi", 24, "Neworking", 4.5),
                new Student("EA002", "Emmanuel", 24, "programming", 4.5)
        );

        when(studentRepository.getStudents()).thenReturn(mockedStudentList);

    //      Act
        List<Student> students = userServices.getStudents();

    //       Assert
        verify(studentRepository, times(1)).getStudents();
        Assertions.assertEquals(mockedStudentList, students);
    }

    @Test
    public void testAddNewStudent(){
//        Arrange
        Student newStudent = new Student("AJ001", "Ajayi", 24, "Networking", 4.5);
        when(studentRepository.isStudent(newStudent)).thenReturn(false);
        doNothing().when(studentRepository).addStudent(newStudent);

//      Act
        boolean added = userServices.addStudent(newStudent);

//        Assert
        Assertions.assertTrue(added);
        verify(studentRepository, times(1)).addStudent(newStudent);
    }

    @Test
    public void testAddExistingStudent(){
//        Arrange
        Student student = new Student("AJ001", "Ajayi", 24, "Networking", 4.5);
        when(studentRepository.isStudent(student)).thenReturn(true);

//        Act
        boolean added = userServices.addStudent(student);

//        Assert
        Assertions.assertFalse(added);
        verify(studentRepository, times(0)).addStudent(student);
    }

    @Test
    public void testGetStudentByCorrectID(){
//        Arrange
        String correctID = "AJ001";
        Student student = new Student(correctID, "Ajayi", 24, "Networking", 4.5);
        Optional<Student> mockedStudent = Optional.of(student);
        when(studentRepository.getStudentByID(correctID)).thenReturn(mockedStudent);

//        Act
        Student retrievedStudent = userServices.getStudentByID(correctID).orElse(null);

//        Assert
        Assertions.assertNotNull(retrievedStudent);
        Assertions.assertEquals(student, retrievedStudent);
        verify(studentRepository).getStudentByID(student.getId());
    }

    @Test
    public void testGetStudentByWrongID(){
//        Arrange
        String wrongID = "WW001";
        Optional<Student> mockedStudent = Optional.empty();
        when(studentRepository.getStudentByID(wrongID)).thenReturn(mockedStudent);

//        Act
        Student retrievedStudent = userServices.getStudentByID(wrongID).orElse(null);

//        Assert
        Assertions.assertNull(retrievedStudent);
        verify(studentRepository).getStudentByID(wrongID);
    }
}
