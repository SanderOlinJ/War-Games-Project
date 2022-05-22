package edu.ntnu.stud.idatt2001.sojohans.wargames.io.readers;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ArmyReaderTest {

    @Test
    @DisplayName("Check if ReadArmyFromFile returns an Army with correct values")
    void checkIfReadArmyFromFileReturnsAnArmyWithCorrectValues() throws IOException {
        Army army = ArmyReader.readArmyFromLocalFileWithNameOfFile("armytest2");

        assertEquals(7, army.getUnits().size());
    }

    @Nested
    class checkIfAllExceptionsAreThrown{

        @Test
        @DisplayName("Exception is thrown when reading from a file that doesn't exist")
        void exceptionThrownWhenReadingFromAFileThatDoesntExist(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromLocalFileWithNameOfFile("nonExistingFile"));
        }

        @Test
        @DisplayName("Exception is thrown when reading from a file that has no name")
        void exceptionThrownWhenReadingAFileThatHasNoName(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromLocalFileWithNameOfFile("     "));
        }

        @Test
        @DisplayName("Exception is thrown when reading from an empty file")
        void exceptionThrownWhenReadingFromAnEmptyFile(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromLocalFileWithNameOfFile("emptyFile"));
        }

        @Test
        @DisplayName("Exception is thrown when reading from a file that has no units")
        void exceptionThrownWhenReadingFromAFileThatHasNoUnits(){

            assertThrows(IOException.class, () -> ArmyReader.readArmyFromLocalFileWithNameOfFile("armyFileWithNoUnits"));
        }

        @Test
        @DisplayName("Exception is thrown when reading from a file with wrong unit data formatting")
        void exceptionThrownWhenReadingFromAFileWithWrongUnitDataFormatting(){

            assertThrows(IOException.class, () -> ArmyReader
                    .readArmyFromLocalFileWithNameOfFile("armyFileWithWrongUnitDataFormatting"));
        }

    }

}