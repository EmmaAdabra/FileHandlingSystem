package controller;

import model.Student;
import model.User;
import repository.IUserRepository;
import services.CSVHandlerType;
import services.IUserServices;
import services.UserServices;
import util.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class UserController {
    User currentUSer;
    ValidateUserInput validate;
    IUserServices userServices;
    ActionProvider actionProvider;

    public UserController(ValidateUserInput validate, IUserRepository userRepository) {
        this.validate = validate;
        this.userServices = new UserServices(userRepository);
        this.actionProvider = new ActionProvider();
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
        user.setIsOnline(true);
        this.currentUSer = user;
        if(chooseCSVHandler()) {
            displayMenu();
        } else {
            System.exit(-1);
        }
    }

    protected void logout() {
        if(currentUSer != null && currentUSer.isOnline()) {
            currentUSer.setIsOnline(false);
        } else {
            System.out.println("You are have already logout");
        }
    }

    protected boolean chooseCSVHandler(){
        boolean csvHandlerNotSelected = true;
        Response<Objects> response = null;

        while (csvHandlerNotSelected) {
            System.out.println();
            System.out.println("--------------- Choose CSV Handler ---------------");
            System.out.println("1. Default CSV Handler");
            System.out.println("2. API CSV Handler (coming soon)");
            System.out.println();
            int option = IterateInput.intInput("Option", 1, 2, validate::validateUserOption);
            switch (option) {
                case 1 -> {
                    response = userServices.setCSVHandler(CSVHandlerType.DEFAULT);
                    csvHandlerNotSelected = false;
                }
                case 2 -> {
                    System.out.println();
                    System.out.println("API CSV Handler Coming soon");
                }
            }

//            Note: modify logic later
            if(response != null) {
                if(response.status) {
                    System.out.println();
                    System.out.println(response.message);
                }
                else {
                    System.out.println();
                    System.out.println("Error occur loading students csv file:\n" + response.message);
                    int opt = chooseCSVHandlerHelper();

                    switch (opt){
                        case 1 -> {return false;}
                        case 2 -> {csvHandlerNotSelected = true;}
                    }
                }
            } else {
                if (response == null && !csvHandlerNotSelected) {
                    System.out.println();
                    System.out.println("Unknown error occur, unable to initialized CSV handler");
                    System.exit(-1);
                }
            }
        }
        return true;
    }

    private int chooseCSVHandlerHelper(){
        System.out.println();
        System.out.println("1. Exit Program");
        System.out.println("2. Try Again");

        return  IterateInput.intInput("Option", 1, 2, validate::validateUserOption);
    }

    protected void displayMenu(){
//        String userDetails = currentUSer.getName() + " (" + currentUSer.getEmail() + ")";
        String heading = "--------------- Main Menu ---------------";
        String[] menuOptions = new String[]{"Add Student", "View Students", "Search For Student", "Edit Student Details",
                "Delete Student", "View Registered Users", "Register User", "Logout"};
        List<Runnable> actionList = new ArrayList<>(Arrays.asList(this::addStudent, this::viewStudents,
                this::searchStudentRecord, this::editStudentDetails, this::deleteStudent, this::viewUsers,
                this::registerUsers, this::logout));

        while (currentUSer.isOnline()) {
            DisplayHelpers.displayMenu(heading, menuOptions, "");
            int userOption = IterateInput.intInput("Option", 1,
                    menuOptions.length, validate::validateUserOption);

            actionProvider.getActions(actionList, userOption).run();

        }
    }

    protected void viewUsers() {
        System.out.println();
        System.out.println("--------------- Registered Users ---------------");
        System.out.println();
        List<User> users = userServices.getUsers();

        if(users.size() > 0) {
            DisplayHelpers.displayList(users);
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
            DisplayHelpers.displayList(students);
        } else {
            System.out.println("No student found");
        }
    }

    protected void addStudent(){
        System.out.println();
        System.out.println("--------------- Add New Student ---------------");
        System.out.println();
        String name = IterateInput.stringInput("Name", validate::validateName);
        int age = CustomScanner.readInt("Age");
        String course = CustomScanner.readString("Course");
        double gpa = CustomScanner.readDouble("GPA");
//        String id = GenerateID.generateID(name, userServices.getStudents());

        var newStudent = new Student(GenerateID.generateID(name,
                TrackNumberOfRegisteredStudent.getTotalRegisteredStudent()), name, age, course, gpa);

        System.out.println();

//        Note: Modify, addStudent should return response and response status should be checked
        if(userServices.addStudent(newStudent)){
            System.out.println("Student added successfully");
            TrackNumberOfRegisteredStudent.incrementStudentCount();
        }
        else {
            System.out.println("Student already exist");
        }
    }

    protected void editStudentDetails(){
        System.out.println();
        String heading = "--------------- Edit Student Details ---------------";
        System.out.println();
        DisplayHelpers.displaySubMenu(heading, new String[]{"Get Student By ID", "Get Student By Name"});

        int option = IterateInput.intInput("Option", 1, 2, validate::validateUserOption);

        switch (option) {
            case 1 -> handleEditStudentByID();
            case 2 -> System.out.println("Coming soon");

        }
    }

    private void handleEditStudentByID() {
        String studentID = IterateInput.stringInput("Student ID", validate::validateStudentID);
        Student student = userServices.getStudentByID(studentID).orElse(null);
        if(student == null){
            System.out.println("Student with ID " + studentID + "not found");
        }
        editStudentHelper(student);
    }

    private void editStudentHelper(Student student) {
        System.out.println();
        System.out.println("Student Details: [" +student + "]");
        String heading = "--------------- Select option to be edited ---------------";
        String[] options = new String[]{"Name", "Course", "Age", "GPA", "Main Menu"};
        boolean whileOnEdit = true;
        while (whileOnEdit){
            DisplayHelpers.displaySubMenu(heading, options);
            int option = IterateInput.intInput("Option", 1, options.length,
                    validate::validateUserOption);
            System.out.println();

            switch (option) {
                case 1 -> {
                    student.setName(IterateInput.stringInput("New Name",
                            validate::validateName));
                    System.out.println("Student name updated successfully");
                }

                case 2 -> {
                    student.setCourse(CustomScanner.readString("New Course"));
                    System.out.println("Student course updated successfully");
                }

                case 3 -> {
                    student.setAge(CustomScanner.readInt("New Age"));
                    System.out.println("Student age updated successfully");
                }

                case 4 -> {
                    student.setGpa(CustomScanner.readDouble("New GPA"));
                    System.out.println("Student GPA updated successfully");
                }

                case 5 -> {
                    var response = userServices.updateStudentRecord();
                    if(!response.status) {
                        System.out.println(response.message);
                    }

                    whileOnEdit = false;
                }
            }
        }
    }

    protected void searchStudentRecord(){
        System.out.println();
        String heading = "--------------- Search for student ---------------";
        String[] searchOption = new String[]{"Search by ID", "Search by Name"};
        DisplayHelpers.displaySubMenu(heading, searchOption);

        int option = IterateInput.intInput("Option", 1,
                searchOption.length, validate::validateUserOption);

        actionProvider.getActions(Arrays.asList(this::searchStudentByID, this::searchStudentByName), option).run();
    }

    private void searchStudentByID() {
        String studentID = IterateInput.stringInput("Student ID", validate::validateStudentID);
        var response = userServices.getStudentByID(studentID);
        Student student = response.orElse(null);

        System.out.println();
        System.out.println("--------------- Search Result ---------------");
        System.out.println();
        if(student == null) {
            System.out.println("No student with the " + studentID);
        }

        System.out.println("[" +student + "]");
    }

    private void searchStudentByName(){
        String studentName = IterateInput.stringInput("Student Name", validate::validateName);
        var searchResult = userServices.getStudentsByName(studentName);

        System.out.println();
        System.out.println("--------------- Search Result ---------------");
        System.out.println();
        if(searchResult.isEmpty()){
            System.out.println(studentName + " not found");
        }

        searchResult.forEach((key, value) -> {
            System.out.println(key + ". " + "[" + value + "]");

            if((key - 1) != searchResult.size() - 1 ){
                System.out.println();
            }
        });
    }

    protected void deleteStudent(){
        System.out.println();
        String heading = "--------------- Delete Student ---------------";
        String[] searchOption = new String[]{"Enter ID", "Enter Name(coming soon)"};
        DisplayHelpers.displaySubMenu(heading, searchOption);

        System.out.println();
        int option = IterateInput.intInput("Option", 1,
                searchOption.length, validate::validateUserOption);
//        System.out.println();

        switch (option){
            case 1 -> {
                String studentID = IterateInput.stringInput("Student ID", validate::validateStudentID);
                deleteStudentByID(studentID);
            }
            case 2 -> System.out.println("Coming soon");
        }
    }

    private void deleteStudentByID(String id){
        System.out.println();
        if(userServices.deleteStudent(id)) {
            System.out.println("Student with ID: " + id + " deleted successfully");
        } else {
            System.out.println("No student with ID: " + id);
        }
    }
}