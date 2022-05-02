package edu.ntnu.stud.idatt2001.sojohans.wargames.io.writers;

import edu.ntnu.stud.idatt2001.sojohans.Utilities;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ArmyWriter {

    private static final String NEWLINE = "\n";
    private static final String DELIMITER = ",";

    public ArmyWriter(){}

    public static boolean doesArmyFileExist(String nameOfFile){

        File file = new File("src/main/resources/edu/ntnu/stud/idatt2001/sojohans/wargames/armyFiles/" +
                Utilities.shortenAndReplaceUnnecessarySymbolsInString(nameOfFile) + ".csv");

        return file.exists();
    }

    public static void writeArmyToFile(Army army, String nameOfFile) throws IOException{

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
