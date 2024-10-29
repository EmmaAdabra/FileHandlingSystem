package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class implements the main.java.com.librarySystem.util.ValidateInput interface to validate user input
 *  for various criteria such as names, email addresses, passwords, and options.
 */
public class ValidateUserInput {

    public Response validateName(String name) {
        if(!(name.length() >= 3 && name.length() <= 20)){
            return new Response(false, "username should be min 3 and max 20 character", null);
        }
        return new Response(true, "success", null);
    }

    public Response validateEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        var pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) {
            return new Response(false,
                     "Email should follow this format \"example@example.com\" ", null);
        }
        return new Response(true, "success", null);
    }

    public Response validatePassword(String password) {
        if(!(password.length() >= 4)) {
            return new Response(false, "password shouldn't be less than 4", null);
        }
        return new Response(true, "success", null);
    }

    public Response validateUserOption(int option, int min, int max) {
        if(!(option >= min && option <= max)){
            return new Response(false, "option should be between " + min + " and " + max, null);
        }
        return new Response(true, "success", null);
    }

    public boolean validateEmptyInput(String input) {
        return input.isEmpty();
    }

    public Response validateIntInput(String input) {
        int result;

        try {
            result = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return new Response(false, "only accept numbers", null);
        }
        return new Response(true, "valid value", result);
    }
}
