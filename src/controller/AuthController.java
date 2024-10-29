package controller;

import model.User;
import repository.IStudentRepository;
import repository.IUserRepository;
import services.AuthServices;
import services.IAuthServices;
import util.IterateInput;
import util.Response;
import util.ValidateUserInput;

public class AuthController {
    private final ValidateUserInput validateInput;
    private final UserController controller;
    private final IAuthServices authServices;

    public AuthController(ValidateUserInput validateInput, IUserRepository userRepository,
                         UserController controller ) {
        this.validateInput = validateInput;
        this.controller = controller;
        this.authServices =  new AuthServices(userRepository);
    }

    public void login(){
        System.out.println();
        System.out.println("--------------- Login Details ---------------");
        String email = IterateInput.stringInput("email", validateInput::validateEmail);
        String password = IterateInput.stringInput("password", validateInput::validatePassword);

        Response verify = authServices.verifyLogin(email, password);
        if(verify.status) {
            User loggedInUser = (User)verify.obj;
            loggedInUser.login();
            controller.handleLogin(loggedInUser);

        } else {
            System.out.println();
            System.out.println(verify.message);
        }
    }
}
