package repository;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public User getUser(String email) {
        for(User user : users) {
            if(user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void addUser(User newUser) {
        users.add(newUser);
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }
}
