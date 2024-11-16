import controller.AuthController;
import controller.UserController;
import repository.IUserRepository;
import repository.UserRepository;
import util.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        TrackNumberOfRegisteredStudent.loadTrackStudentFile();
        var actionProviders = new ActionProvider();
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
            int option = IterateInput.intInput("Option", 1, 3,
                    validateUserInput::validateUserOption);

            actionProviders.getActions(Arrays.asList(userController::signUp,
                    authController::login, () -> {System.exit(0);}), option).run();
        }
    }
}
