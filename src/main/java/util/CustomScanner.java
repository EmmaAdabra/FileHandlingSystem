package util;

import java.util.Scanner;

/**
 * This class is used to read users inputs
 */
public class CustomScanner {
    private final static Scanner scanner = new Scanner(System.in);
    private static ValidateUserInput validate = new ValidateUserInput();
    public static String readString(String prompt){
        String value;
        while(true) {
            System.out.print(prompt +": ");
            value = scanner.nextLine().toUpperCase().trim();
            if(validate.validateEmptyInput(value)){
                System.out.println(prompt + " can't be empty");
            }
            else {
                break;
            }
        }
        return value;
    }

    public static int readInt(String prompt){
        int option;

        while (true) {
            System.out.print(prompt +": ");
            String userOption = scanner.nextLine().trim();

            if(validate.validateEmptyInput(userOption)){
                System.out.println(prompt + " can't be empty");
                continue;
            }

            var response = validate.validateIntInput(userOption);

            if(!response.status) {
                System.out.println("Only accept numbers");
                continue;
            }

            option = (Integer) response.obj;

            break;

        }
        return option;
    }

    public static double readDouble(String prompt){
        double option;

        while (true) {
            System.out.print(prompt +": ");
            String userOption = scanner.nextLine().trim();

            if(validate.validateEmptyInput(userOption)){
                System.out.println(prompt + " can't be empty");
                continue;
            }

            var response = validate.validateDoubletInput(userOption);

            if(!response.status) {
                System.out.println("Only accept numbers");
                continue;
            }

            option = (Double) response.obj;

            break;
        }

        return option;
    }
}
