package util;

import java.io.File;
import java.io.IOException;

public class CSVHelpers {
    public static boolean fileExist(String filePath) {
        File file = new File(filePath);

        return file.exists();
    }

    public static Response createFile(String filePath) {
        try {
            File file = new File(filePath);
            file.createNewFile();
        } catch (IOException e) {
            return new Response(false, e.getMessage(), null);
        }

        return new Response(true, "success", null);
    }

    public static boolean isEmpty(String file_path) {
        var file = new File(file_path);

        return !fileExist(file_path) || file.length() == 0;
    }
}
