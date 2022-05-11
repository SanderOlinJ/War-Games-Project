package edu.ntnu.stud.idatt2001.sojohans;

import java.io.File;

public class Utilities {

    public static File convertStringToFile(String string){
        String newStr = convertStringToFileName(string);
        return new File("src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/armyFiles/" +
                shortenAndReplaceNonAlphaNumericSymbolsInString(newStr) + ".csv");
    }

    public static String shortenAndReplaceNonAlphaNumericSymbolsInString(String str){
        if (str == null || str.length() == 0){
            return "";
        }
        return str.replaceAll("[^A-Za-z0-9]","");
    }

    public static boolean doesArmyFileExist(String nameOfFile){
        File file = Utilities.convertStringToFile(nameOfFile);
        return file.exists();
    }

    public static String convertStringToFileName(String string){
        char[] charArray = string.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return shortenAndReplaceNonAlphaNumericSymbolsInString(new String(charArray));
    }

    public static boolean stringDoesNotContainAnyNonAlphaNumericSymbols(String str){
        str = str.replaceAll(" ", "");
        return str.length() != Utilities.shortenAndReplaceNonAlphaNumericSymbolsInString(str).length();
    }

    public static boolean stringDoesNotContainSymbolsOtherThanNumbers(String str){
        String newStr = str.replaceAll("[^0-9]", "");
        return str.length() != newStr.length();
    }
}
