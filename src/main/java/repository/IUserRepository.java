package repository;

import model.User;

import java.util.List;

public interface IUserRepository {
    User getUser(String email);
    void addUser(User newUser);
    List<User> getAllUsers();
}
