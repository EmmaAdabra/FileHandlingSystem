package repository;

import model.User;

public interface IUserRepository {
    User getUser(String email);
    void addUser(User newUser);
}
