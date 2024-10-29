package services;

import repository.IStudentRepository;
import repository.IUserRepository;

public class UserServices {
    IStudentRepository studentRepository;
    IUserRepository userRepository;

    public UserServices(IStudentRepository studentRepository, IUserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }
}

