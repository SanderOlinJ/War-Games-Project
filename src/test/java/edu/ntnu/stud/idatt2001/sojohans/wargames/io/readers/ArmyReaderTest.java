package edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ArmyReaderTest {

    @Test
    void checkIfReadArmyFromFileReturnsAnArmyWithCorrectValues() throws IOException {
        Army army = ArmyReader.readArmyFromLocalFileWithNameOfFile("armytest");

        assertEquals(7, army.getUnits().size());
    }

    @Nested
    class checkIfAllExceptionsAreThrown{

        @Test
        void isExceptionThrownWhenReadingFromFileThatDoesntExist(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromLocalFileWithNameOfFile("nonExistingFile"));
        }

        @Test
        void isExceptionThrownWhenReadingAFileThatHasNoName(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromLocalFileWithNameOfFile("     "));
        }

        @Test
        void isExceptionThrownWhenReadingFromAnEmptyFile(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromLocalFileWithNameOfFile("emptyFile"));
        }

        @Test
        void isExceptionThrownWhenReadingFromAFileThatHasNoUnits(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromLocalFileWithNameOfFile("armyFileWithNoUnits"));
        }

        @Test
        void isExceptionThrownWhenReadingFromAFileWithWrongUnitDataFormatting(){

            assertThrows(IOException.class, () -> ArmyReader
                    .readArmyFromLocalFileWithNameOfFile("armyFileWithWrongUnitDataFormatting"));
        }

    }

}