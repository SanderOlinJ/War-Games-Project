package edu.ntnu.stud.idatt2001.sojohans.wargames.io.writers;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for writing an Army object to file.
 */
public class ArmyWriter {

    private static final String NEWLINE = "\n";
    private static final String DELIMITER = ",";

    public ArmyWriter(){}

    /**
     * Method for writing an Army to a .csv file.
     * @param army The Army being written to file.
     * @param nameOfFile Name of file where Army is to be written.
     * @throws IOException If Army is null or file name is empty.
     */
    public static void writeArmyToFile(Army army, String nameOfFile) throws IOException{
        if (army == null){
            throw new IOException("Army cannot be null!");
        }
        if (nameOfFile == null || Utilities.shortenAndReplaceNonAlphaNumericSymbolsInString(nameOfFile).isEmpty()){
            throw new IOException("File name cannot be null or empty!");
        }
        StringBuilder stringBuilder =  new StringBuilder();
        stringBuilder.append(army.getName()).append(NEWLINE);

        String unitsToFile = getUnitsWithNumberOfOccurrencesToBeWrittenToFile(army.getUnits());
        stringBuilder.append(unitsToFile);

        try(FileWriter fileWriter = new FileWriter(Utilities.convertStringToFile
                (Utilities.shortenAndReplaceNonAlphaNumericSymbolsInString(nameOfFile)))){
            fileWriter.write(stringBuilder.toString());
        } catch (IOException exception){
            throw new IOException("Unable to write Army to file: " + exception.getMessage());
        }
    }


    /**
     * Method for getting Units with number of occurrences as a String.
     * Format: Class Name, Name, Number of occurrence.
     * @param units List of Units to be written to String.
     * @return String of Units to be written to File.
     * @throws IOException If List of Unit is null or empty.
     */
    private static String getUnitsWithNumberOfOccurrencesToBeWrittenToFile(List<Unit> units)
    throws IOException{
        if (units == null || units.size() == 0){
            throw new IOException("List of units cannot be empty");
        }
        Map<Unit,Integer> unitsAndNumberOfOccurrences = new HashMap<>();
        for (Unit unit : units){
            if (!unitsAndNumberOfOccurrences.containsKey(unit)){
                unitsAndNumberOfOccurrences.put(unit, 1);
            } else {
                unitsAndNumberOfOccurrences.put(unit, unitsAndNumberOfOccurrences.get(unit) + 1);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        unitsAndNumberOfOccurrences.forEach((unit, integer) ->
                {
                    UnitType unitType = null;
                    switch (unit.getClass().getSimpleName()){
                        case "InfantryUnit" -> unitType = UnitType.INFANTRY_UNIT;
                        case "RangedUnit" -> unitType = UnitType.RANGED_UNIT;
                        case "CavalryUnit" -> unitType = UnitType.CAVALRY_UNIT;
                        case "CommanderUnit" -> unitType = UnitType.COMMANDER_UNIT;
                    }
                stringBuilder.append(unitType).append(DELIMITER)
                        .append(unit.getName()).append(DELIMITER)
                        .append(integer).append(NEWLINE);
                });

        return stringBuilder.toString();
    }
}
