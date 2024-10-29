import controller.UserController;
import infrastructure.DefaultCSVHandler;
import infrastructure.ICSVHandler;
import model.Student;
import repository.IStudentRepository;
import repository.IUserRepository;
import repository.StudentRepository;
import repository.UserRepository;
import util.CSVHelpers;
import util.ValidateUserInput;

import java.util.List;

public class App {
    public static void main(String[] args) {
        var validateUserInput = new ValidateUserInput();
        IUserRepository userRepository = new UserRepository();
        IStudentRepository studentRepository = new StudentRepository();
        var userController = new UserController(validateUserInput, userRepository, studentRepository);

        userController.signUp();
        userController.displayMenu();
    }
}
