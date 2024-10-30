import controller.AuthController;
import controller.UserController;
import infrastructure.DefaultCSVHandler;
import infrastructure.ICSVHandler;
import model.Student;
import repository.IStudentRepository;
import repository.IUserRepository;
import repository.StudentRepository;
import repository.UserRepository;
import util.CSVHelpers;
import util.DisplayHelpers;
import util.IterateInput;
import util.ValidateUserInput;

import java.util.List;

public class App {
    public static void main(String[] args) {
        var validateUserInput = new ValidateUserInput();
        IUserRepository userRepository = new UserRepository();
        var userController = new UserController(validateUserInput, userRepository);
        var authController = new AuthController(validateUserInput, userRepository, userController);

        System.out.println();
        System.out.println("                  STUDENT MANAGEMENT SYSTEM                   ");
        String heading = "---------------- Sign up / Login ---------------";
        String[] menuOptions = new String[]{"Sign up", "Login", "Exit"};

        while (true) {
            DisplayHelpers.displayMenu(heading, menuOptions, "");
            int option = IterateInput.intInput("Option", 1, 3, validateUserInput::validateUserOption);

            switch (option){
                case 1 -> userController.signUp();
                case 2 -> authController.login();
                case 3 -> {
                    System.exit(0);
                }
            }
        }
    }
}
