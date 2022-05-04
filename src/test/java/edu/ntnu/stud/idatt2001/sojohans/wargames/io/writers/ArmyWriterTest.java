package edu.ntnu.stud.idatt2001.sojohans.wargames.io.writers;

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
        String emptyString = "      ";
        assertThrows(IOException.class, () -> ArmyWriter.writeArmyToFile(army, emptyString));
    }
}