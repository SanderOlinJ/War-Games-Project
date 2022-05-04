package edu.ntnu.stud.idatt2001.sojohans.wargames.io.writers;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        if (nameOfFile == null || Utilities.shortenAndReplaceUnnecessarySymbolsInString(nameOfFile).isEmpty()){
            throw new IOException("File name cannot be null or empty");
        }
        StringBuilder stringBuilder =  new StringBuilder();
        stringBuilder.append(army.getName()).append(NEWLINE)
                .append(army.getUnits().size()).append(NEWLINE);

        army.getUnits().forEach(unit -> stringBuilder
                .append(unit.getClass().getSimpleName()).append(DELIMITER)
                .append(unit.getName()).append(DELIMITER)
                .append(unit.getHealth()).append(NEWLINE));

        try(FileWriter fileWriter = new FileWriter(Utilities.convertStringToFile
                (Utilities.shortenAndReplaceUnnecessarySymbolsInString(nameOfFile)))){
            fileWriter.write(stringBuilder.toString());

        } catch (IOException exception){
            throw new IOException("Unable to write Army to file: " + exception.getMessage());
        }
    }
}
