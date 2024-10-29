package services;

import model.User;

import java.util.List;

public interface IUserServices {
    boolean addUser(User user);
    List<User> getUsers();
}
