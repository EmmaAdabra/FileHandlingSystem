package controller;

import model.Student;
import model.User;
import repository.IUserRepository;
import services.IUserServices;
import services.UserServices;
import util.*;

import java.util.List;

public class UserController {
    User currentUSer;
    ValidateUserInput validate;
    IUserServices userServices;

    public UserController(ValidateUserInput validate, IUserRepository userRepository) {
        this.validate = validate;
        this.userServices = new UserServices(userRepository);

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

    protected void handleLogin(User user) {
        user.login();
        this.currentUSer = user;
        if(chooseCSVHandler()) {
            displayMenu();
        } else {
            System.exit(1);
        }
    }

    protected void logout() {
        if(currentUSer != null && currentUSer.isOnline()) {
            currentUSer.logout();
        } else {
            System.out.println("You are have already logout");;
        }
    }

    protected boolean chooseCSVHandler(){
        final String MAIN_CSV_HANDLER = "main";
        final String API_CSV_HANDLER = "api";
        boolean csvHandlerNotSelected = true;
        Response response = null;

        while (csvHandlerNotSelected) {
            System.out.println();
            System.out.println("--------------- Choose CSV Handler ---------------");
            System.out.println("1. Default CSV Handler");
            System.out.println("2. CSV API Handler (coming soon)");
            int option = IterateInput.intInput("Option", 1, 3, validate::validateUserOption);

            switch (option) {
                case 1 -> {
                    response = userServices.setCSVHandler(MAIN_CSV_HANDLER);
                }
                case 2 -> {
                    System.out.println();
                    System.out.println("API CSV Handler Coming soon");
                }
            }

//            Note: modify logic later
            if(response != null) {
                if(response.status) {
                    csvHandlerNotSelected = false;
                } else {
                    System.out.println();
                    System.out.println("Error occur loading students csv file:\n" + response.message);

                    int opt = chooseCSVHandlerHelper();

                    switch (opt) {
                        case 1 -> {
                            return false;
                        }

                        case 2 -> {
                            System.out.println();
                            System.out.println("No other available CSV Handler");
                            chooseCSVHandlerHelper();
                        }
                    }
                }
            }
        }

        System.out.println();
        System.out.println("csv/students.csv loaded successfully");
        return true;
    }

    private int chooseCSVHandlerHelper(){
        System.out.println();
        System.out.println("1. Exit Program");
        System.out.println("2. Try a different csv handler");

        return  CustomScanner.readInt("Option");
    }

    protected void displayMenu(){
        String userDetails = currentUSer.getName() + " (" + currentUSer.getEmail() + ")";
        String heading = "--------------- Main Menu ---------------";
        String[] menuOptions = new String[]{"View Students", "View Registered Users", "Register User", "Logout"};

        while (currentUSer.isOnline()) {
            DisplayHelpers.displayMenu(heading, menuOptions, userDetails);
            int userOption = IterateInput.intInput("Option", 1,
                    menuOptions.length, validate::validateUserOption);

            switch (userOption) {
                case 1 -> viewStudents();
                case 2 -> viewUsers();
                case 3 -> registerUsers();
                case 4 -> logout();
            }
        }
    }

    protected void viewUsers() {
        System.out.println();
        System.out.println("--------------- Registered Users ---------------");
        System.out.println();
        List<User> users = userServices.getUsers();

        if(users.size() > 0) {
            DisplayHelpers.displayObjects(users);
        } else {
            System.out.println("No register user");
        }
    }

    protected void registerUsers() {
        signUp();
    }

    protected void viewStudents() {
        System.out.println();
        System.out.println("--------------- All students ---------------");
        System.out.println();
        List<Student> students =  userServices.getStudents();

        if(students.size() > 0) {
            DisplayHelpers.displayObjects(students);
        } else {
            System.out.println("No student found");
        }
    }
}
