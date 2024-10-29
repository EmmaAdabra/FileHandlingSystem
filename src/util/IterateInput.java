package util;

import java.util.function.Function;


public class IterateInput {
    static CustomScanner scanner = new CustomScanner();
    static public String stringInput(String prompt, Function<String, Response> validate) {
        String value;
        Response response;
        while (true) {
            value = scanner.readString(prompt);
            response = validate.apply(value);
            if (response.status)
                break;
            System.out.println(response.message);
        }
        return value;
    }

    static public int intInput(String prompt, int min, int max,
                               TriFunction<Integer, Integer, Integer, Response> validate) {
        int option;
        Response response;
        while (true) {
            option = scanner.readInt(prompt);
            response = validate.apply(option, min, max);
            if (response.status)
                break;
            System.out.println(response.message);
        }

        return option;
    }
}
