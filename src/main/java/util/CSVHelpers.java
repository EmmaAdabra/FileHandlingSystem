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
        File parentDir = file.getParentFile();

        if(parentDir != null) {
            if(!parentDir.mkdir() && parentDir.exists()) {
                throw new IOException("Error occur creating directory: " + parentDir.getAbsolutePath());
            }
        }

        return file.createNewFile();
    }

    public static boolean isEmpty(String file_path) {
        var file = new File(file_path);

        return !fileExist(file_path) || file.length() == 0;
    }
}
