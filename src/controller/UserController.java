package controller;

import model.User;
import repository.IStudentRepository;
import repository.IUserRepository;
import services.IUserServices;
import services.UserServices;
import util.IterateInput;
import util.ValidateUserInput;

public class UserController {
    User currentUSer;
    ValidateUserInput validate;
    IUserServices userServices;

    public UserController(ValidateUserInput validate, IUserRepository userRepository,
                          IStudentRepository studentRepository) {
        this.validate = validate;
        this.userServices = new UserServices(studentRepository, userRepository);
    }

    public void signUp(){
        System.out.println();
        System.out.println("Enter your details");

//        get user details
        String name = IterateInput.stringInput("Name", validate::validateName);
        String email = IterateInput.stringInput("Email", validate::validateEmail).toLowerCase();
        String password = IterateInput.stringInput("Password", validate::validatePassword);

//        create user
        var newUser = new User(name, email, password);

//       register user
        boolean register = userServices.addUser(newUser);

        if(register) {
            System.out.println(newUser.getName() + " have been registered successfully");
        } else {
            System.out.println(newUser.getEmail() + " already exist");
        }
    }
}
