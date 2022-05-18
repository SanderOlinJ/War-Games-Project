package edu.ntnu.stud.idatt2001.sojohans;

import javafx.scene.text.Text;

import java.io.File;

public class Utilities {

    public static File convertStringToArmyFile(String string){
        String newStr = convertStringToFileName(string);
        return new File("src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/armyFiles/" +
                shortenAndReplaceNonAlphaNumericSymbolsInString(newStr) + ".csv");
    }

    public static String shortenAndReplaceNonAlphaNumericSymbolsInString(String str){
        if (str == null || str.length() == 0){
            return "";
        }
        str = str.replaceAll("\\s","");
        str = str.replaceAll("[^A-Za-z0-9]","");
        return str;
    }

    public static boolean doesArmyFileExist(String nameOfFile){
        File file = Utilities.convertStringToArmyFile(nameOfFile);
        return file.exists();
    }

    public static String convertStringToFileName(String string){
        char[] charArray = string.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return shortenAndReplaceNonAlphaNumericSymbolsInString(new String(charArray));
    }

    public static boolean checkIfStringContainsAnyNonAlphaNumericSymbols(String str){
        str = str.replaceAll("\\s","");
        return str.length() != Utilities.shortenAndReplaceNonAlphaNumericSymbolsInString(str).length();
    }

    public static boolean checkIfStringContainsSymbolsOtherThanNumbers(String str){
        str = str.replaceAll("\\s","");
        String newStr = str.replaceAll("[^0-9]", "");
        return str.length() != newStr.length();
    }

    public static String getPathToTerrainImageFile(String terrainAsString){
        terrainAsString = convertStringToFileName(terrainAsString);
        return String.format("file:src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/images/" +
                terrainAsString + ".png");
    }


}
