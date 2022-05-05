package edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ArmyReaderTest {

    @Test
    void checkIfReadArmyFromFileReturnsAnArmyWithCorrectValues() throws IOException {
        Army army = ArmyReader.readArmyFromFile("army");

        System.out.println(army);
    }

}