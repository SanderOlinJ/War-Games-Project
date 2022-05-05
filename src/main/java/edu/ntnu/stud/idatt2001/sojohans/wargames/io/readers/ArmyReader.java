package edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.*;
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

    private static final String NEWLINE = "\n";
    private static final String DELIMITER = ",";

    public ArmyReader(){}


    public static Army readArmyFromFile(String nameOfFile)
    throws IOException{

        if (!Utilities.doesArmyFileExist(nameOfFile)){
            throw new IOException("File does not exist!");
        }
        if (nameOfFile == null || Utilities.shortenAndReplaceUnnecessarySymbolsInString(nameOfFile).isEmpty()){
            throw new IOException("File name cannot be empty");
        }
        File file = Utilities.convertStringToFile(nameOfFile);
        List<String> armyInfoInFile = new ArrayList<>();

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

        if (armyInfoInFile.size() == 1){
            throw new IOException("File does not contain units!");
        }

        String armyName = armyInfoInFile.get(0);
        String armyNameShortened = Utilities.shortenAndReplaceUnnecessarySymbolsInString(armyName);
        if (armyNameShortened.isEmpty()){
            throw new IOException("Army name cannot be empty");
        }
        if (armyNameShortened.length() < armyName.length()){
            throw new IOException("Army name can only contain alphanumeric symbols + Æ,Ø,Å");
        }
        Army army = new Army(armyNameShortened);

        for (int i = 1; i < armyInfoInFile.size(); i++) {
            String line = armyInfoInFile.get(i);
            String[] values = line.split(DELIMITER);
            if (values.length != 3) {
                throw new IOException("Error: Line data '" + line + "' is invalid." +
                        "Make sure each line is in the form of 'Unit type,name,health,attack,armor,'");
            }
            String unitType = values[0];
            String unitName = values[1];
            String unitNameShortened = Utilities.shortenAndReplaceUnnecessarySymbolsInString(unitName);
            if (unitNameShortened.isEmpty()){
                throw new IOException("Army name cannot be empty");
            }
            if (unitNameShortened.length() < unitName.length()){
                throw new IOException("Army name can only contain alphanumeric symbols + Æ,Ø,Å");
            }
            int numberOfOccurrences;
            try {
                numberOfOccurrences = Integer.parseInt(values[2]);
            } catch (NumberFormatException exception) {
                throw new IOException("Error parsing number of occurrences for unit at line " + i);
            }
            Unit unit = null;
            for (int j = 0; j < numberOfOccurrences; j++) {
                switch (unitType) {
                    case "InfantryUnit" -> army.addUnit(new InfantryUnit(unitName, 100));
                    case "RangedUnit" -> army.addUnit(new RangedUnit(unitName, 100));
                    case "CavalryUnit" -> army.addUnit(new CavalryUnit(unitName, 100));
                    case "CommanderUnit" -> army.addUnit(new CommanderUnit(unitName, 100));
                    default -> throw new IOException("Could not instantiate units");
                }
            }
        }
        return army;
    }
}
