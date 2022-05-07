package edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ArmyReaderTest {

    @Test
    void checkIfReadArmyFromFileReturnsAnArmyWithCorrectValues() throws IOException {
        Army army = ArmyReader.readArmyFromFile("army");

        assertEquals(7, army.getUnits().size());
    }

    @Nested
    class checkIfAllExceptionsAreThrown{

        @Test
        void isExceptionThrownWhenReadingFromFileThatDoesntExist(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromFile("nonExistingFile"));
        }

        @Test
        void isExceptionThrownWhenReadingAFileThatHasNoName(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromFile("     "));
        }

        @Test
        void isExceptionThrownWhenReadingFromAnEmptyFile(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromFile("emptyFile"));
        }

        @Test
        void isExceptionThrownWhenReadingFromAFileThatHasNoUnits(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromFile("armyFileWithNoUnits"));
        }

        @Test
        void isExceptionThrownWhenReadingFromAFileWithWrongUnitDataFormatting(){

            assertThrows(IOException.class, () -> ArmyReader
                    .readArmyFromFile("armyFileWithWrongUnitDataFormatting"));
        }

    }

}