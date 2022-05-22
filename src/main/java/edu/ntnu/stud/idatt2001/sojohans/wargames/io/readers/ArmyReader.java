package edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitFactory;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for reading an Army object from file.
 */
public class ArmyReader {

    private static final String DELIMITER = ",";

    /**
     * Method for checking if an Army file exists.
     * @param nameOfFile Name of the file to be checked.
     * @return True if it exists, false if not.
     */
    public static boolean doesArmyFileExist(String nameOfFile){
        File file = Utilities.convertStringToArmyFile(nameOfFile);
        return file.exists();
    }
    /**
     * <p>
     *     Method for reading a local Army from file.
     * </p>
     * <p>
     *     Local in this case meaning stored under the 'armyFiles' folder in resources.
     * </p>
     * @param nameOfFile Name of the file to be read from.
     * @return Army that was stored in the file
     * @throws IOException If file does not exist, empty or file name is not supported.
     */
    public static Army readArmyFromLocalFileWithNameOfFile(String nameOfFile)
    throws IOException{

        if (!doesArmyFileExist(nameOfFile)){
            throw new IOException(nameOfFile + " file does not exist!");
        }
        if (nameOfFile == null || Utilities.shortenAndReplaceNonAlphaNumericCharactersInString(nameOfFile).isEmpty()){
            throw new IOException("File name cannot be empty");
        }
        String nameOfFileShortened = Utilities.convertStringToFileName(nameOfFile);
        if (nameOfFileShortened.length() < nameOfFile.length()){
            throw new IOException("File name can only contain alphanumeric symbols!");
        }

        File file = Utilities.convertStringToArmyFile(nameOfFile);
        return readArmy(file);
    }

    /**
     * Method for reading an Army file using the File as parameter.
     * @param file File containing the Army to be read.
     * @return Army that was stored in the file
     * @throws IOException If the file is null or if it does not exist.
     */
    public static Army readArmyFileWithPath(File file) throws IOException{
        if (file == null){
            throw new IOException("File cannot be null!");
        }
        if (!file.exists()){
            throw new IOException(file.getName() + " does not exist!");
        }
        return readArmy(file);
    }

    /**
     * <p>
     *     Method for reading an Army from file.
     * </p>
     * <p>
     *     Method instantiates an Army from file only if this format is followed:
     * </p>
     * <p>
     *     Army name.
     * </p>
     * <p>
     *     UnitType, Name, Health, Number of occurrences.
     * </p>
     * @param file File to be read from
     * @return Army that was stored in the file
     * @throws IOException if file does not contain units, army name is empty or contains unsupported character,
     * or if line formatting in file is wrong.
     */
    private static Army readArmy(File file) throws IOException {
        List<String> armyInfoInFile = new ArrayList<>();

        //  Method first reads the file to a List
        readFromFileToList(file, armyInfoInFile);

        if (armyInfoInFile.size() == 1){
            throw new IOException( file.getName() + " does not contain units!");
        }
        if (armyInfoInFile.get(0).isBlank()){
            throw new IOException("Army name cannot be empty in " + file.getName());
        }
        if (Utilities.checkIfStringContainsAnyNonAlphaNumericCharacters(armyInfoInFile.get(0))){
            throw new IOException("Army name can only contain alphanumeric symbols in " + file.getName());
        }

        //  An Army is then instantiated using the first value from the file, the name of the Army.
        String armyName = armyInfoInFile.get(0);
        Army army = new Army(armyName);

        for (int i = 1; i < armyInfoInFile.size(); i++) {

            //  Each following line is then checked if empty
            //  then split up into 4 values that are then stored in an Array.
            String line = armyInfoInFile.get(i);
            if (line.isEmpty()){
                throw new IOException("Line empty at line " + i + " in " + file.getName());
            }

            String[] values = line.split(DELIMITER);
            if (values.length != 4) {
                throw new IOException("Error: Line '" + line + "' is invalid in " + file.getName() +
                        "\nMake sure each line is in the form of 'UnitType,Name,Health,Number of occurrence'");
            }
            //  These 4 values should be: 1) UnitType, 2) Name of the Unit, 3) Health of the Unit
            //  and 4) Number of occurrences for this Unit.

            String unitTypeAsString = values[0];
            UnitType unitType;
            try{
                unitType = UnitType.valueOf(unitTypeAsString);
            } catch (IllegalArgumentException exception){
                throw new IOException("Unit type not recognized in " + file.getName());
            }

            String unitName = values[1];
            if (unitName.trim().isEmpty()){
                throw new IOException("Unit name cannot be empty in " + file.getName());
            }
            if (Utilities.checkIfStringContainsAnyNonAlphaNumericCharacters(unitName)){
                throw new IOException("Unit name can only contain alphanumeric symbols in " + file.getName());
            }

            int health;
            try {
                health = Integer.parseInt(values[2]);
            } catch (NumberFormatException exception){
                throw new IOException("Error parsing health for unit at line " + i + " in " + file.getName());
            }

            int numberOfOccurrences;
            try {
                numberOfOccurrences = Integer.parseInt(values[3]);
            } catch (NumberFormatException exception) {
                throw new IOException("Error parsing number of occurrences for unit at line " + i + " in " +
                        file.getName());
            }

            // If there were no formatting errors in the file, then the unit is sent to the UnitFactory to be produced.
            army.addAllUnits(UnitFactory.getCertainAmountOfUnits(unitType, unitName, health, numberOfOccurrences));
        }
        return army;
    }

    /**
     * Method for reading file to List of Strings
     * @param file File to be read from
     * @param armyInfoInFile List to add Strings to.
     * @throws IOException If file is empty or if file could not be read.
     */
    private static void readFromFileToList(File file, List<String> armyInfoInFile) throws IOException {
        try(Scanner scanner = new Scanner(file)){
            if (!scanner.hasNext()){
                throw new IOException( file.getName() + " is empty!");
            }
            while (scanner.hasNext()){
                armyInfoInFile.add(scanner.nextLine());
            }
        } catch (IOException exception){
            throw new IOException("File could not be read: " + exception.getMessage());
        }
    }

    /**
     * Method for reading Army file names to list from the Army Overview file.
     * @return List of Strings containing all Army File names of Armies stored locally.
     * @throws IOException If file is empty or if file could not be read.
     */
    public static List<String> readArmyFileNamesFromOverviewFile() throws IOException{
        List<String> armyFileNames = new ArrayList<>();
        File file = new File("src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                "wargames/armyFiles/armyFilesOverview.csv");

        readFromFileToList(file, armyFileNames);

        return armyFileNames;
    }

}
