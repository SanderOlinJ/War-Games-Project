package edu.ntnu.stud.idatt2001.sojohans;

import java.io.File;

/**
 * Class for methods used in the application that can best be described as Utilities.
 */
public class Utilities {

    /**
     * Method converts a String (Army File name) to a File.
     * The File is by default located where every other locally stored Army is.
     * @param string Name of the file
     * @return File with the file name, located under the 'armyFiles' folder under resources.
     */
    public static File convertStringToArmyFile(String string){
        String newStr = convertStringToFileName(string);
        return new File("src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/armyFiles/" +
                shortenAndReplaceNonAlphaNumericCharactersInString(newStr) + ".csv");
    }

    /**
     * Method for shortening of Strings and replacing all non-alphanumeric characters.
     * @param str String to be edited.
     * @return String without spaces, whitespaces and non-alphanumeric characters.
     */
    public static String shortenAndReplaceNonAlphaNumericCharactersInString(String str){
        if (str == null || str.length() == 0){
            return "";
        }
        str = str.replaceAll("\\s","");
        str = str.replaceAll("[^A-Za-z\\d]","");
        return str;
    }


    /**
     * Method for converting a String to a String suitable as a File name.
     * @param string String to be converted.
     * @return String suitable as a File name.
     */
    public static String convertStringToFileName(String string){
        char[] charArray = string.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return shortenAndReplaceNonAlphaNumericCharactersInString(new String(charArray));
    }

    /**
     * Method for checking if a String contains any non-alphanumeric characters.
     * @param str String to be checked.
     * @return True if it contains any non-alphanumeric characters, false if not.
     */
    public static boolean checkIfStringContainsAnyNonAlphaNumericCharacters(String str){
        str = str.replaceAll("\\s","");
        return str.length() != Utilities.shortenAndReplaceNonAlphaNumericCharactersInString(str).length();
    }

    /**
     * Method for checking if a String contains any other characters than number.
     * @param str String to be checked.
     * @return True if it contains any other characters than number, false if not.
     */
    public static boolean checkIfStringContainsCharactersOtherThanNumbers(String str){
        str = str.replaceAll("\\s","");
        String newStr = str.replaceAll("[^0-9]", "");
        return str.length() != newStr.length();
    }
}
