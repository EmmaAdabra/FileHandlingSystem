package services;

import model.User;
import repository.IStudentRepository;
import repository.IUserRepository;

public class UserServices implements IUserServices {
    IStudentRepository studentRepository;
    IUserRepository userRepository;

    public UserServices(IStudentRepository studentRepository, IUserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean addUser(User newUser) {
        var user = userRepository.getUser(newUser.getEmail());

        if(user == newUser) {
            userRepository.addUser(newUser);
            return true;
        }

        return false;
    }
}

