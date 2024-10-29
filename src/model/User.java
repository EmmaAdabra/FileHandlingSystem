package model;

import java.util.Objects;

public class User {
    private final String name;
    private final String email;
    private final String password;
    private boolean isOnline = false;

    public void login() {
        isOnline = true;
    }

    public void logout() {
        isOnline = false;
    }


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nEmail: " + email;
    }
}
