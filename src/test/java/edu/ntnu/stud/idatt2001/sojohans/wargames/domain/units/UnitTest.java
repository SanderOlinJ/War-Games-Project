package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitAttackException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war.Army;
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
            infantryUnit1.attack(infantryUnit2);

            assertThrows(UnitAttackException.class, () ->infantryUnit1.attack(infantryUnit2));
        }

        @Test
        @DisplayName("Attacking Unit has invalid health during attack," +
                "throws UnitAttackException")
        public void attackingUnitHasInvalidHealthDuringAttack(){
            RangedUnit rangedUnit = new RangedUnit("Archer", 100);
            InfantryUnit infantryUnit = new InfantryUnit("Commander",1);
            rangedUnit.attack(infantryUnit);

            assertThrows(UnitAttackException.class, () -> infantryUnit.attack(rangedUnit));
        }

        @Test
        @DisplayName("Test to see if attack() returns correct health value")
        public void testToSeeIfAttackReturnsCorrectHealthValue(){
            CommanderUnit guardian = new CommanderUnit("Guardian",180);
            CommanderUnit savathun = new CommanderUnit("Savathun",180);
            guardian.attack(savathun);

            assertEquals(165,savathun.getHealth());
        }

        @Nested
        class doesTerrainAffectAttackAndResistBonusAsExpected {
            @Test
            void doesInfantryUnitGetIncreasedAttackBonusInForest() {

                Army army = new Army("Army");
                army.addUnit(new InfantryUnit("Infantry", 100));

                army.getUnits().forEach(unit -> unit.setTerrainType(TerrainType.FOREST));
                assertEquals(6, army.getUnits().get(0).getAttackBonus());
            }

            @Test
            void doesInfantryUnitGetIncreasedResistBonusInForest() {

                Army army = new Army("Army");
                army.addUnit(new InfantryUnit("Infantry", 100));

                army.getUnits().forEach(unit -> unit.setTerrainType(TerrainType.FOREST));
                assertEquals(3, army.getUnits().get(0).getResistBonus());
            }

            @Test
            void doesRangedUnitGetIncreasedAttackBonusOnHill() {

                Army army = new Army("Army");
                army.addUnit(new RangedUnit("Ranged", 100));

                army.getUnits().forEach(unit -> unit.setTerrainType(TerrainType.HILL));
                assertEquals(6, army.getUnits().get(0).getAttackBonus());
            }

            @Test
            void doesRangedUnitGetDecreasedAttackBonusInForest() {

                Army army = new Army("Army");
                army.addUnit(new RangedUnit("Ranged", 100));

                army.getUnits().forEach(unit -> unit.setTerrainType(TerrainType.FOREST));
                assertEquals(1, army.getUnits().get(0).getAttackBonus());
            }

            @Test
            void doesCavalryUnitGetIncreasedAttackBonusOnPlains() {

                Army army = new Army("Army");
                army.addUnit(new CavalryUnit("Cavalry", 100));

                army.getUnits().forEach(unit -> unit.setTerrainType(TerrainType.PLAINS));
                assertEquals(9, army.getUnits().get(0).getAttackBonus());
            }

            @Test
            void doesCavalryUnitGetNoAttackBonusInForest() {

                Army army = new Army("Army");
                army.addUnit(new CavalryUnit("Cavalry", 100));

                army.getUnits().forEach(unit -> unit.setTerrainType(TerrainType.FOREST));
                assertEquals(0, army.getUnits().get(0).getResistBonus());
            }
        }
    }
}