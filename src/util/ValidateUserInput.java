package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class implements the main.java.com.librarySystem.util.ValidateInput interface to validate user input
 *  for various criteria such as names, email addresses, passwords, and options.
 */
public class ValidateUserInput {

    public Response validateName(String name) {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        final String tempName = name.replace(" ", "").toLowerCase();

        if((name.length() < 2 || name.length() > 20)){
            return new Response(false, "username should be min 2 and max 20 character", null);
        }

        for(char letter : tempName.toCharArray()) {
            if(alphabet.indexOf(letter) == -1) {
                return new Response(false,
                        "Illegal character found, name should only contain [Aa-zZ]", null);
            }
        }

        final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]{2,20}\\s[A-Za-z]{1,20}$");
        Matcher matcher = NAME_PATTERN.matcher(name);

        if(!matcher.matches()) {
            return new Response(false, "Name format: 'John Doe' or 'john d' case don't matter", null);
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

    public Response validateDoubletInput(String input) {
        double result;

        try {
            result = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return new Response(false, "only accept numbers", null);
        }
        return new Response(true, "valid value", result);
    }
}
