package edu.ntnu.stud.idatt2001.sojohans.wargames.io.writers;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import org.junit.jupiter.api.Test;
import java.io.IOException;

class ArmyWriterTest {

    @Test
    void runWriteArmyToFile() throws IOException {
        Army army = new Army("Human Army");

        ArmyWriter.writeArmyToFile(army, army.getName());
    }
}