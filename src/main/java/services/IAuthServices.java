package services;

import util.Response;

public interface IAuthServices {
    Response verifyLogin(String email, String password);
}
