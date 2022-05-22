package edu.ntnu.stud.idatt2001.sojohans.wargames.io.writers;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.CommanderUnit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.InfantryUnit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.RangedUnit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.SpearFighterUnit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ArmyWriterTest {

    @Test
    @DisplayName("Exception is thrown when running WriteArmyToFile with an Army that is null")
    void exceptionIsThrownWhenRunningWriteArmyToFileWithAnArmyThatIsNull(){

        assertThrows(IOException.class, () -> ArmyWriter.writeArmyToFile(null,"File"));
    }

    @Test
    @DisplayName("Exception is thrown when running WriteArmyToFile with a file name that is null")
    void exceptionIsThrownWhenRunningWriteArmyToFileWithAFileNameThatIsNull(){
        Army army = new Army("Army");
        assertThrows(IOException.class, () -> ArmyWriter.writeArmyToFile(army, null));
    }

    @Test
    @DisplayName("Exception is thrown when running WriteArmyToFile with a file name that is empty")
    void exceptionIsThrownWhenRunningWriteArmyToFileWithAFileNameThatIsEmpty(){
        Army army = new Army("Army");
        army.addUnit(new SpearFighterUnit("Swordsman",100));
        army.addUnit(new SpearFighterUnit("Swordsman",100));
        String emptyString = "      ";
        assertThrows(IOException.class, () -> ArmyWriter.writeArmyToFile(army, emptyString));
    }

    @Test
    @DisplayName("Run WriteArmyToFile")
    void runWriteArmyToFile() throws IOException{
        Army army = new Army("armytest");
        army.addUnit(new SpearFighterUnit("Swordsman",100));
        army.addUnit(new SpearFighterUnit("Swordsman",100));
        army.addUnit(new SpearFighterUnit("SwordsmaN",100));
        army.addUnit(new RangedUnit("Archer",100));
        army.addUnit(new RangedUnit("Archer",100));
        army.addUnit(new RangedUnit("Archer",101));
        army.addUnit(new CommanderUnit("Archer",101));

        ArmyWriter.writeArmyToFile(army, army.getName());
    }
}