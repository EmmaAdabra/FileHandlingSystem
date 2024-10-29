package util;

public class DisplayHelpers {
    public static void displayMenu(String title, String[] options, String userDetails){
        System.out.println();
        if(userDetails != ""){
            System.out.println(userDetails);
        }
        System.out.println(title);
        System.out.println();
        for(int i = 0; i < options.length; i++) {
            System.out.println(i+1 + ". " + options[i]);
        }
        System.out.println();
    }

    public static void displaySubMenu(String title, String[] options){
//        System.out.println();
        System.out.println(title);
        for(int i = 0; i < options.length; i++) {
            System.out.println(i+1 + ". " + options[i]);
        }
        System.out.println();
    }
}
