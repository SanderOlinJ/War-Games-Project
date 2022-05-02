package edu.ntnu.stud.idatt2001.sojohans;

import java.io.File;

public class Utilities {

    public static File convertStringToFile(String string){
        return new File("src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/armyFiles/" +
                string + ".csv");
    }

    public static String shortenAndReplaceUnnecessarySymbolsInString(String str){
        if (str == null || str.length() == 0){
            return "";
        }
        char[] charArray = str.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);

        String newStr = new String(charArray);
        return newStr.replaceAll("[^A-Za-z0-9]","");
    }
}
