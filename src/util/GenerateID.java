package util;

public class GenerateID {
    public static String generateID(String name, int studentSize){
        if(name == null) {
            return null;
        }

        String[] names = name.split(" ");

        if(names.length < 2) {
            return null;
        }

        String nameInitial = String.valueOf(names[0].charAt(0)).toUpperCase()
                + String.valueOf(names[1].charAt(0)).toUpperCase();

        int IDNumber = (studentSize * 10) / 10;

        String numberPartOfID = IDNumber < 10 ? "00"+studentSize : IDNumber < 100 ? "0" + studentSize:
                "" + studentSize;

        return nameInitial + numberPartOfID;
    }
}
