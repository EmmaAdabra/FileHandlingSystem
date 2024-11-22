package util;

public class ValidateCSVData {
    public static boolean validateCSVHeader(String csvHeader){
        final String CSV_HEADER = "ID,NAME,AGE,COURSE,GPA";

        if(!(csvHeader.isEmpty() || csvHeader.equalsIgnoreCase(CSV_HEADER))){
            return true;
        }

        return false;
    }
}
