package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @Nested
    class InvalidAttributesInConstructorAndSetHealth{


        @Test
        @DisplayName("Constructor takes in invalid health value," +
                "throws WarGamesException")
        public void constructorTakesInInvalidHealthValueThrowsWarGamesException(){
            assertThrows(IllegalArgumentException.class, () -> {
                InfantryUnit infantryUnit = new InfantryUnit("Spearman", -2, 15, 10);
            });
        }

        @Test
        @DisplayName("setHealth() takes in invalid health value," +
                "throws WarGamesException")
        public void setHealthTakesInInvalidHealthValueThrowsWarGamesException(){
            InfantryUnit infantryUnit = new InfantryUnit("Commander",180, 20,12);
            assertThrows(IllegalArgumentException.class, () -> infantryUnit.setHealth(-180));
        }





    }
}