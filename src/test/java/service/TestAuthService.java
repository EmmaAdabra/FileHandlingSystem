package service;

import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.IUserRepository;
import services.AuthServices;
import services.IAuthServices;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestAuthService {
    @Mock
    IUserRepository userRepository;

    IAuthServices authService;

    @BeforeEach
    public void setUp(){
        authService = new AuthServices(userRepository);
    }

    @Test
    public void testVerifyLoginWithCorrectDetails(){
//        Arrange
        String userEmail = "emma@gmail.com";
        String userPassword = "1234";
        var user = new User("Emmanuel A", userEmail, userPassword);

        when(userRepository.getUser(userEmail)).thenReturn(user);

//        Act
        var testResponse = authService.verifyLogin(userEmail, userPassword);

//        Assert
        verify(userRepository, times(1)).getUser(userEmail);
        Assertions.assertTrue(testResponse.status);
        Assertions.assertEquals(user, testResponse.obj);
    }

    @Test
    public void testVerifyLoginWithWrongDetails(){
//        Arrange
        String wrongEmail = "wrongemail@gmail.com";
        String userPassword = "1234";

        when(userRepository.getUser(wrongEmail)).thenReturn(null);

//        Act

        var testResponse = authService.verifyLogin(wrongEmail, userPassword);

//        Assert
        verify(userRepository, times(1)).getUser(wrongEmail);
        Assertions.assertFalse(testResponse.status);
        Assertions.assertNull(testResponse.obj);
    }
}
