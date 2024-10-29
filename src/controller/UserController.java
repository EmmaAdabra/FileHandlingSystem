package controller;

import model.User;
import repository.IStudentRepository;
import repository.IUserRepository;
import services.IUserServices;
import services.UserServices;
import util.DisplayHelpers;
import util.IterateInput;
import util.ValidateUserInput;

import java.util.List;

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

        System.out.println();
        if(register) {
            System.out.println(newUser.getName() + " have been registered successfully");
        } else {
            System.out.println(newUser.getEmail() + " already exist");
        }
    }

    protected void displayMenu(){
        String heading = "--------------- Main Menu ---------------";
        String[] menuOptions = new String[]{"View Registered Users", "Register User", "Logout"};

        while (currentUSer.isOnline()) {
            DisplayHelpers.displayMenu(heading, menuOptions, "");
            int userOption = IterateInput.intInput("Option", 1,
                    menuOptions.length, validate::validateUserOption);

            switch (userOption) {
                case 1 -> viewUsers();
                case 2 -> registerUsers();
                case 3 -> logout();
            }
        }
    }

    protected void viewUsers() {
        System.out.println();
        List<User> users = userServices.getUsers();

        if(users.size() > 0) {
            DisplayHelpers.displayUsers(users);
        } else {
            System.out.println("No register user");
        }
    }

    protected void registerUsers(){
        signUp();
    }

    protected void handleLogin(User user) {
        user.login();
        this.currentUSer = user;
        displayMenu();
    }

    protected void logout() {
        if(currentUSer != null && currentUSer.isOnline()) {
            currentUSer.logout();
        } else {
            System.out.println("You are have already logout");;
        }
    }
}
