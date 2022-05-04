package edu.ntnu.stud.idatt2001.sojohans.wargames.io.writers;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.CommanderUnit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.InfantryUnit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.RangedUnit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ArmyWriterTest {

    @Test
    void runWriteArmyToFile() throws IOException {
        Army army = new Army("Human Army");

        ArmyWriter.writeArmyToFile(army, army.getName());
    }


    @Test
    void runWriteArmyToFileButWithAnArmyThatIsNull(){

        assertThrows(IOException.class, () -> ArmyWriter.writeArmyToFile(null,"File"));
    }

    @Test
    void runWriteArmyToFileButWithAFileNameThatIsNull(){
        Army army = new Army("Army");
        assertThrows(IOException.class, () -> ArmyWriter.writeArmyToFile(army, null));
    }

    @Test
    void runWriteArmyToFileButWithAFileNameThatIsEmpty(){
        Army army = new Army("Army");
        army.addUnit(new InfantryUnit("Swordsman",100));
        army.addUnit(new InfantryUnit("Swordsman",100));
        army.addUnit(new InfantryUnit("SwordsmaN",100));
        army.addUnit(new RangedUnit("Archer",100));
        army.addUnit(new RangedUnit("Archer",100));
        army.addUnit(new RangedUnit("Archer",101));
        army.addUnit(new CommanderUnit("Archer",101));
        String emptyString = "      ";
        assertThrows(IOException.class, () -> ArmyWriter.writeArmyToFile(army, emptyString));
    }

    @Test
    void testSomething() throws IOException{
        Army army = new Army("Army");
        army.addUnit(new InfantryUnit("Swordsman",100));
        army.addUnit(new InfantryUnit("Swordsman",100));
        army.addUnit(new InfantryUnit("SwordsmaN",100));
        army.addUnit(new RangedUnit("Archer",100));
        army.addUnit(new RangedUnit("Archer",100));
        army.addUnit(new RangedUnit("Archer",101));
        army.addUnit(new CommanderUnit("Archer",101));

        ArmyWriter.writeArmyToFile(army, army.getName());
    }
}