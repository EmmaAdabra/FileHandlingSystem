package util;

import java.io.*;

public class TrackNumberOfRegisteredStudent {
    private static int totalRegisteredStudent;
    private static final String TRACK_REGISTERED_STUD_FILE = ".TotalRegisteredStudent.txt";

    public static void createTrackRegisteredStudentsFile() throws IOException {
        File trackStudentFile = new File(TRACK_REGISTERED_STUD_FILE);
        trackStudentFile.createNewFile();
    }

    public static boolean studentTrackFileExist(){
        File trackStudentFile = new File(TRACK_REGISTERED_STUD_FILE);

        return trackStudentFile.exists();
    }

    public static void incrementStudentCount(){
        totalRegisteredStudent += 1;
        writeToTrackStudentFile();
    }

    public static void writeToTrackStudentFile(){
        try (FileWriter writer = new FileWriter(TRACK_REGISTERED_STUD_FILE)){
            writer.write(Integer.toString(totalRegisteredStudent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadTrackStudentFile(){
        if(studentTrackFileExist()){
            try(BufferedReader reader = new BufferedReader(new FileReader(TRACK_REGISTERED_STUD_FILE))){
                String totalRegisteredStud;

                while ((totalRegisteredStud = reader.readLine()) != null){
                    totalRegisteredStudent = Integer.parseInt(totalRegisteredStud);
                }

                if(totalRegisteredStudent == 0){
                    totalRegisteredStudent += 1;
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } else {
            totalRegisteredStudent += 1;
            try {
                createTrackRegisteredStudentsFile();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }

    public static int getTotalRegisteredStudent(){
        return totalRegisteredStudent;
    };
}
