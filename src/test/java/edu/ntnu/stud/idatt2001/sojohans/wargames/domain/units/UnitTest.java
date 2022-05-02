package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.WarGamesException;
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
            assertThrows(IllegalArgumentException.class, () ->
                    new InfantryUnit("Spearman", -2, 15, 10));
        }

        @Test
        @DisplayName("Constructor takes in invalid attack value," +
                "throws WarGamesException")
        public void constructorTakesInInvalidAttackValueThrowsWarGamesException(){
            assertThrows(IllegalArgumentException.class, () ->
                    new RangedUnit("Archer", 100, 0, 20));
        }

        @Test
        @DisplayName("setHealth() takes in invalid health value," +
                "throws WarGamesException")
        public void setHealthTakesInInvalidHealthValueThrowsWarGamesException(){
            InfantryUnit infantryUnit = new InfantryUnit("Commander",180, 20,12);
            assertThrows(IllegalArgumentException.class, () -> infantryUnit.setHealth(-180));
        }
    }

    @Nested
    class AttackMethodTestCases{
        @Test
        @DisplayName("Opponent has invalid health during attack," +
                "throws WarGamesException")
        public void opponentHasInvalidHealthDuringAttack(){
            InfantryUnit infantryUnit1 = new InfantryUnit("Swordsman",100);
            InfantryUnit infantryUnit2 = new InfantryUnit("Spear fighter",1);
            infantryUnit1.attack(infantryUnit2);

            assertThrows(WarGamesException.class, () ->infantryUnit1.attack(infantryUnit2));
        }

        @Test
        @DisplayName("Attacking Unit has invalid health during attack," +
                "throws WarGamesException")
        public void attackingUnitHasInvalidHealthDuringAttack(){
            RangedUnit rangedUnit = new RangedUnit("Archer", 100);
            InfantryUnit infantryUnit = new InfantryUnit("Commander",1);
            rangedUnit.attack(infantryUnit);

            assertThrows(WarGamesException.class, () -> infantryUnit.attack(rangedUnit));
        }

    }
}