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

    public ArmyReader(){}


    public static Army readArmyFromLocalFileWithNameOfFile(String nameOfFile)
    throws IOException{

        if (!Utilities.doesArmyFileExist(nameOfFile)){
            throw new IOException("File does not exist!");
        }
        if (nameOfFile == null || Utilities.shortenAndReplaceNonAlphaNumericSymbolsInString(nameOfFile).isEmpty()){
            throw new IOException("File name cannot be empty");
        }
        String nameOfFileShortened = Utilities.convertStringToFileName(nameOfFile);
        if (nameOfFileShortened.length() < nameOfFile.length()){
            throw new IOException("File name can only contain alphanumeric symbols and Æ,Ø,Å");
        }


        File file = Utilities.convertStringToArmyFile(nameOfFile);
        return readArmy(file);
    }

    public static Army readArmyFileWithPath(File file) throws IOException{
        if (file == null){
            throw new IOException("File cannot be null!");
        }
        if (!file.exists()){
            throw new IOException("File does not exist!");
        }
        return readArmy(file);
    }

    private static Army readArmy(File file) throws IOException {
        List<String> armyInfoInFile = new ArrayList<>();

        readFromFileToList(file, armyInfoInFile);
        if (armyInfoInFile.size() == 1){
            throw new IOException("File does not contain units!");
        }

        if (armyInfoInFile.get(0).trim().isEmpty()){
            throw new IOException("Army name cannot be empty");
        }
        if (Utilities.checkIfStringContainsAnyNonAlphaNumericSymbols(armyInfoInFile.get(0))){
            throw new IOException("Army name can only contain alphanumeric symbols and Æ,Ø,Å");
        }

        String armyName = armyInfoInFile.get(0);
        Army army = new Army(armyName);


        for (int i = 1; i < armyInfoInFile.size(); i++) {

            String line = armyInfoInFile.get(i);
            if (line.isEmpty()){
                throw new IOException("Line empty at line " + i);
            }

            String[] values = line.split(DELIMITER);
            if (values.length != 3) {
                throw new IOException("Error: Line data '" + line + "' is invalid." +
                        "Make sure each line is in the form of 'Unit type,name,number of occurrences,'");
            }

            String unitTypeAsString = values[0];
            String unitName = values[1];
            if (unitName.trim().isEmpty()){
                throw new IOException("Unit name cannot be empty");
            }

            UnitType unitType;
            try{
                unitType = UnitType.valueOf(unitTypeAsString);
            } catch (IllegalArgumentException exception){
                throw new IOException("Unit type not recognized");
            }
            if (Utilities.checkIfStringContainsAnyNonAlphaNumericSymbols(unitName)){
                throw new IOException("Unit name can only contain alphanumeric symbols");
            }


            int numberOfOccurrences;
            try {
                numberOfOccurrences = Integer.parseInt(values[2]);
            } catch (NumberFormatException exception) {
                throw new IOException("Error parsing number of occurrences for unit at line " + i);
            }

            army.addAllUnits(UnitFactory.getCertainAmountUnits(unitType, unitName, numberOfOccurrences));
        }
        return army;
    }

    private static void readFromFileToList(File file, List<String> armyInfoInFile) throws IOException {
        try(Scanner scanner = new Scanner(file)){
            if (!scanner.hasNext()){
                throw new IOException("File is empty!");
            }
            while (scanner.hasNext()){
                armyInfoInFile.add(scanner.nextLine());
            }

        } catch (IOException exception){
            throw new IOException("File could not be read: " + exception.getMessage());
        }
    }

    public static List<String> readArmyFileNamesFromOverviewFile() throws IOException{
        List<String> armyFileNames = new ArrayList<>();
        File file = new File("src/main/resources/edu/ntnu/stud/idatt2001/sojohans/" +
                "wargames/armyFiles/armyFilesOverview.csv");

        readFromFileToList(file, armyFileNames);

        return armyFileNames;
    }
}
