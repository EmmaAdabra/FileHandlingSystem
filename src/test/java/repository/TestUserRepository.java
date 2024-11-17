package repository;

import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

//import static junit.framework.TestCase.*;

public class TestUserRepository {
    IUserRepository userRepository;

    @BeforeEach
    public void setUp(){
        userRepository = new UserRepository();
    }

    @Test
    public void saveAndRetrieveNonExistingUserTest(){
//        Arrange
        String userEmail = "ajayi@gmail.com";
        User newUser = new User("Ajayi", userEmail, "1234");

//        Act
        User user = userRepository.getUser(userEmail);
        userRepository.addUser(newUser);

//        Assert
        Assertions.assertNull(user);
        Assertions.assertTrue(userRepository.getAllUsers().contains(newUser));
        Assertions.assertEquals(1, userRepository.getAllUsers().size());
    }


    @Test
    public void getExistingUserByEmailTest(){
//        Arrange
        String userEmail = "emma@gmail.com";
        User user = new User("emmanuel", userEmail, "1234");
        userRepository.addUser(user);

//        Act
        User existingUser = userRepository.getUser(userEmail);

//        Assert
        Assertions.assertNotNull(existingUser);
        Assertions.assertEquals(userEmail, existingUser.getEmail());
    }

    @Test
    public void getNonExistingUserByEmailTest(){
//        Arrange
        String userEmail = "test@gmail.com";

//        Act
        User NonExistingUser = userRepository.getUser(userEmail);

//        Assert
        Assertions.assertNull(NonExistingUser);
    }

    @Test
    public void getAllUsersTest(){
//        Arrange
        userRepository.addUser(new User("emmanuel", "emma@gmail.com", "1234"));
        userRepository.addUser(new User("Ajayi", "ajayi@gmail.com", "1234"));

//        Act
        List<User> users = userRepository.getAllUsers();

//        Assert
        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals(2, users.size());
    }


}
