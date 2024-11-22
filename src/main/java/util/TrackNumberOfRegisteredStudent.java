package util;

import infrastructure.GlobalErrorHandler;

import java.io.*;

public class TrackNumberOfRegisteredStudent {
    private static int totalRegisteredStudent;
    private static final String TRACK_REGISTERED_STUD_FILE = ".totalRegisteredStudent.txt";

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

    public static boolean writeToTrackStudentFile(){
        try (FileWriter writer = new FileWriter(TRACK_REGISTERED_STUD_FILE)){
            writer.write(Integer.toString(totalRegisteredStudent));
            return true;
        } catch (IOException e) {
            System.out.println();
            System.out.println("Error occur writing to "+ TRACK_REGISTERED_STUD_FILE + "\n" +
                    "This will result in inaccurate student ID generation" +  "\n" + e.getMessage());
        } catch (Exception e){
            GlobalErrorHandler.handleException(e);
        }

        return false;
    }

    public static boolean loadTrackStudentFile(){
        if(studentTrackFileExist()){
            try(BufferedReader reader = new BufferedReader(new FileReader(TRACK_REGISTERED_STUD_FILE))){
                String totalRegisteredStud;

                while ((totalRegisteredStud = reader.readLine()) != null){
                    totalRegisteredStudent = Integer.parseInt(totalRegisteredStud);
                }

                if(totalRegisteredStudent == 0){
                    totalRegisteredStudent += 1;
                }
            } catch (IOException e) {
                System.out.println();
                System.out.println("Error occur reading "+ TRACK_REGISTERED_STUD_FILE + "\n" +
                        "This will result in inaccurate student ID generation" +  "\n" + e.getMessage());
                return false;
            }
            catch (Exception e){
                GlobalErrorHandler.handleException(e);
                return false;
            }

        } else {
            totalRegisteredStudent += 1;
            try {
                createTrackRegisteredStudentsFile();
            } catch (IOException e){
                System.out.println();
                System.out.println("Error occur creating "+ TRACK_REGISTERED_STUD_FILE + "\n" +
                        "This will result in inaccurate student ID generation" +  "\n" + e.getMessage());
                return false;
            }
            catch (Exception e){
                GlobalErrorHandler.handleException(e);
                return false;
            }
        }


        return true;
    }

    public static int getTotalRegisteredStudent(){
        return totalRegisteredStudent;
    }
}
