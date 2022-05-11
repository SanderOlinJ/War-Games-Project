package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitAttackException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @Nested
    class InvalidAttributesInConstructorAndSetHealth{

        @Test
        @DisplayName("Constructor takes in invalid health value," +
                "throws UnitAttackException")
        public void constructorTakesInInvalidHealthValueThrowsWarGamesException(){
            assertThrows(IllegalArgumentException.class, () ->
                    new InfantryUnit("Spearman", -2, 15, 10));
        }

        @Test
        @DisplayName("Constructor takes in invalid attack value," +
                "throws UnitAttackException")
        public void constructorTakesInInvalidAttackValueThrowsWarGamesException(){
            assertThrows(IllegalArgumentException.class, () ->
                    new RangedUnit("Archer", 100, 0, 20));
        }

        @Test
        @DisplayName("Constructor takes in invalid armor value," +
                "throws UnitAttackException")
        public void constructorTakesInInvalidArmorValueThrowsWarGamesException(){
            assertThrows(IllegalArgumentException.class, () ->
                new CavalryUnit("Light Cavalry",100,20, -50));
        }

        @Test
        @DisplayName("setHealth() takes in invalid health value," +
                "throws UnitAttackException")
        public void setHealthTakesInInvalidHealthValueThrowsWarGamesException(){
            InfantryUnit infantryUnit = new InfantryUnit("Commander",180, 20,12);
            assertThrows(IllegalArgumentException.class, () -> infantryUnit.setHealth(-180));
        }
    }

    @Nested
    class AttackMethodTestCases{
        @Test
        @DisplayName("Opponent has invalid health during attack," +
                "throws UnitAttackException")
        public void opponentHasInvalidHealthDuringAttack(){
            InfantryUnit infantryUnit1 = new InfantryUnit("Swordsman",100);
            InfantryUnit infantryUnit2 = new InfantryUnit("Spear fighter",1);
            infantryUnit1.attack(infantryUnit2,TerrainType.PLAINS);

            assertThrows(UnitAttackException.class, () ->infantryUnit1.attack(infantryUnit2,TerrainType.PLAINS));
        }

        @Test
        @DisplayName("Attacking Unit has invalid health during attack," +
                "throws UnitAttackException")
        public void attackingUnitHasInvalidHealthDuringAttack(){
            RangedUnit rangedUnit = new RangedUnit("Archer", 100);
            InfantryUnit infantryUnit = new InfantryUnit("Commander",1);
            rangedUnit.attack(infantryUnit,TerrainType.PLAINS);

            assertThrows(UnitAttackException.class, () -> infantryUnit.attack(rangedUnit,TerrainType.PLAINS));
        }

        @Test
        @DisplayName("Test to see if attack() returns correct health value")
        public void testToSeeIfAttackReturnsCorrectHealthValue(){
            CommanderUnit guardian = new CommanderUnit("Guardian",180);
            CommanderUnit savathun = new CommanderUnit("Savathun",180);
            guardian.attack(savathun, TerrainType.HILL);

            assertEquals(165,savathun.getHealth());
        }
    }
}