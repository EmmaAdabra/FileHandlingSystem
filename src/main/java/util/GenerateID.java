package util;

import model.Student;

import java.util.List;

public class GenerateID {
    private static String computeID(String nameInitial, int studentSize){
        int IDNumber = (studentSize * 10) / 10;

        String numberPartOfID = IDNumber < 10 ? "00"+studentSize : IDNumber < 100 ? "0" + studentSize:
                "" + studentSize;

        return nameInitial + numberPartOfID;
    }

//    private static boolean isIDExists(String id, List<Student> students) {
//        return students.stream().anyMatch(student -> student.getId().equals(id));
//    }
    public static String generateID(String name, int totalRegisteredStudent){
        int studentSize = totalRegisteredStudent;

        if(name == null) {
            return null;
        }

        String[] names = name.split(" ");

        if(names.length < 2) {
            return null;
        }

        String nameInitial = String.valueOf(names[0].charAt(0)).toUpperCase()
                + String.valueOf(names[1].charAt(0)).toUpperCase();


       String studentID = computeID(nameInitial, studentSize);


        return studentID;
    }



}
