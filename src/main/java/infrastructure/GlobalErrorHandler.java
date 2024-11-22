package infrastructure;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GlobalErrorHandler {
    public static void handleException(Exception e) {
        System.err.println("An unexpected error occurred: " + e.getMessage());
        logError(e);
    }

    private static void logError(Exception e) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("error.log", true))) {
            pw.println("Error occurred: " + e.getMessage());
            e.printStackTrace(pw);
        } catch (IOException logEx) {
            System.err.println("Failed to write to error log: " + logEx.getMessage());
        }
    }
}
