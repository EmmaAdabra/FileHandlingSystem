package controller;

import model.User;
import repository.IStudentRepository;
import repository.IUserRepository;
import services.UserServices;
import util.ValidateUserInput;

public class UserController {
    User currentUSer;
    ValidateUserInput validate;
    UserServices userServices;

    public UserController(ValidateUserInput validate, UserServices userServices,
                          IUserRepository userRepository, IStudentRepository studentRepository) {
        this.validate = validate;
        this.userServices = userServices;
    }

    public void signUp(){

    }
}
