package services;

import model.User;
import repository.IUserRepository;
import util.Response;

public class AuthServices implements IAuthServices {
    IUserRepository userRepository;

    public AuthServices(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response<Object> verifyLogin(String email, String password) {
        User user = userRepository.getUser(email);

        if(user == null || !user.getPassword().equals(password)) {
            return new Response<>(false, "Invalid user details", null);
        }

        return new Response<>(true, "success", user);
    }
}
