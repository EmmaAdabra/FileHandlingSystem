package util;

import java.io.File;
import java.io.IOException;

public class CSVHelpers {
    public static boolean fileExist(String filePath) {
        File file = new File(filePath);

        return file.exists();
    }

//   Note: modify this logic
    public static boolean createFile(String filePath) throws IOException {
        File file = new File(filePath);

        if(file.getParentFile().mkdir() || file.getParentFile().exists()) {
            return file.createNewFile();
        }

        return true;
    }

    public static boolean isEmpty(String file_path) {
        var file = new File(file_path);

        return !fileExist(file_path) || file.length() == 0;
    }
}
