package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {

    @Nested
    class InitiatingArmyClass {

        @Test
        @DisplayName("Initiate Army with a List of Units")
        public void initiateArmyWithAListOfUnits(){
            List<Unit> units = new ArrayList<>();
            units.add(new CommanderUnit("Garen",180));
            units.add(new CavalryUnit("Jarvan",100));
            units.add(new InfantryUnit("Fiora",100));
            Army demacians = new Army("Demacians",units);

            assertEquals(3, demacians.getUnits().size());
        }

        @Test
        @DisplayName("Initiate Army with a null or empty List")
        public void initiateArmyWithANullOrEmptyList() {
            List<Unit> emptyUnitsList = new ArrayList<>();
            assertThrows(IllegalArgumentException.class, () -> new Army("Toussaint", emptyUnitsList));

            assertThrows(IllegalArgumentException.class, () -> new Army("Rivia", null));
        }
    }

    @Nested
    class AddingUnitsToArmy {

        @Nested
        class AddingValidUnitsToArmy {

            @Test
            @DisplayName("Check if single Unit is added to Army's List of Units")
            public void checkIfSingleUnitIsAddedToArmysListOfUnits() {
                Army guardians = new Army("Guardians");
                assertFalse(guardians.hasUnits());

                guardians.addUnit(new CommanderUnit("Cayde-6",180));
                assertTrue(guardians.hasUnits());
            }

            @Test
            @DisplayName("Check if a List of Units is added to Army's List of Units")
            public void checkIfAListOfUnitsIsAddedToArmysListOfUnits(){
                Army guardians = new Army("Guardians");
                List<Unit> units = new ArrayList<>();
                units.add(new CommanderUnit("Ikora",180));
                units.add(new CommanderUnit("Zavala",180));
                assertFalse(guardians.hasUnits());

                guardians.addAllUnits(units);
                assertTrue(guardians.hasUnits());
            }

            @Test
            @DisplayName("Check if Units added or instantiated with are in fact deep copied," +
                    "and Army's List of Units is not coupled to the List it was instantiated with")
            public void checkIfUnitsAreDeepCopiedAndArmysListIsNotCoupled(){
                List<Unit> units = new ArrayList<>();
                units.add(new InfantryUnit("Swordsman",100));
                units.add(new CavalryUnit("Cavalry",100));
                units.add(new RangedUnit("Archer",100));
                units.add(new CommanderUnit("Commander",180));
                Army army = new Army("Army", units);

                //CHECKING IF THE LISTS SIZES CHANGES
                InfantryUnit infantryUnit = new InfantryUnit("Swordsman",100);
                army.addUnit(new InfantryUnit("Swordsman",100));
                assertEquals(5,army.getUnits().size());
                assertEquals(4,units.size());

                //CHECKING IF OBJECTS FROM LIST ARE DEEP COPIED
                army.getUnits().get(0).setHealth(40);
                assertEquals(40, army.getUnits().get(0).getHealth());
                assertEquals(100, units.get(0).getHealth());

                //CHECKING IF SINGLE ADDED UNIT IS DEEP COPIED
                army.getUnits().get(4).setHealth(50);
                assertEquals(50, army.getUnits().get(4).getHealth());
                assertEquals(100, infantryUnit.getHealth());

            }
        }
        @Nested
        class AddingInvalidUnitsToArmy{

            @Test
            @DisplayName("Adding null Unit to Army, throws IllegalArgumentException")
            public void addingNullUnitToArmyThrowsIllegalArgumentException(){
                Army redania = new Army("Redania");
                assertThrows(IllegalArgumentException.class, () -> redania.addUnit(null));
            }

            @Test
            @DisplayName("Adding null and empty List to Army, throws IllegalArgumentException")
            public void addingNullAndEmptyListToArmyThrowsIllegalArgumentException(){
                Army kaedwen = new Army("Kaedwen");
                assertThrows(IllegalArgumentException.class, () -> kaedwen.addAllUnits(null));

                List<Unit> emptyUnitList = new ArrayList<>();
                assertThrows(IllegalArgumentException.class, () -> kaedwen.addAllUnits(emptyUnitList));
            }

            @Test
            @DisplayName("Adding List with a null Unit to Army, throws IllegalArgumentException")
            void addingListWithANullUnitToArmyThrowsIllegalArgumentException(){
                List<Unit> listWithANullUnit = new ArrayList<>();
                listWithANullUnit.add(null);
                Army army = new Army("Army");
                assertThrows(IllegalArgumentException.class, () -> army.addAllUnits(listWithANullUnit));
            }
        }
    }

    @Nested
    class RemoveMethodTestCases {

        @Nested
        class RemovingValidUnits {

            @Test
            @DisplayName("Remove Already Instantiated Unit From Army")
            public void removeAlreadyInstantiatedUnitFromArmy(){
                InfantryUnit infantryUnit = new InfantryUnit("Swordsman",100);
                Army army = new Army("Army");
                army.addUnit(infantryUnit);
                assertTrue(army.hasUnits());

                army.remove(infantryUnit);
                assertFalse(army.hasUnits());
            }

            @Test
            @DisplayName("Remove Unit that has not been instantiated first")
            public void removeUnitThatHasNotBeenInstantiatedFirst() {
                Army army = new Army("Army");
                army.addUnit(new InfantryUnit("Infantry", 100));
                assertTrue(army.hasUnits());

                army.remove(new InfantryUnit("Infantry", 100));
                assertFalse(army.hasUnits());
            }

        }

        @Test
        @DisplayName("Removing Unit from Army it is not in")
        public void removingUnitFromArmyItIsNotIn(){
            Army army = new Army("Army");
            army.addUnit(new RangedUnit("Archer",100));

            assertThrows(IllegalArgumentException.class, () -> army.remove(
                    new InfantryUnit("Swordsman",100)));
        }
    }

    @Test
    @DisplayName("Does getRandom() return a Unit from the Army")
    public void doesGetRandomReturnAUnitFromTheArmy(){
        List<Unit> units = new ArrayList<>();
        units.add(new CavalryUnit("Knight 1",100));
        units.add(new CavalryUnit("Knight 2",100));
        Army army = new Army("Army", units);

        Unit randomUnit = army.getRandom();
        assertTrue(army.getUnits().contains(randomUnit));
    }

    @Test
    @DisplayName("Does the returned List from getUnits() let you change Unit attributes")
    public void doesReturnedListFromGetUnitsLetYouChangeUnitAttributes(){
        Army army = new Army("Army");
        army.addUnit(new InfantryUnit("Swordsman",100));

        List<Unit> units = army.getUnits();
        units.get(0).setHealth(40);

        assertEquals(40, army.getUnits().get(0).getHealth());
        assertEquals(40, units.get(0).getHealth());
    }

    @Test
    @DisplayName("Does the returned List from getUnits() stay coupled to Army's private List")
    public void doesReturnedListFromGetUnitsStayCoupledToArmysPrivateList(){
        Army army = new Army("Army");
        army.addUnit(new InfantryUnit("Swordsman",100));

        List<Unit> units = army.getUnits();
        units.add(new RangedUnit("Archer",100));

        assertEquals(1, army.getUnits().size());
        assertEquals(2, units.size());
    }

    @Test
    @DisplayName("Test to see if getRandom throws exception if Army's List of Units is empty")
    public void testToSeeIfGetRandomThrowsExceptionIfArmysListOfUnitsIsEmpty(){
        Army army = new Army("Army");
        assertThrows(IllegalArgumentException.class, army::getRandom);
    }
    @Nested
    class lambdaReturnMethodsForSpecificUnitsTests{

        @Test
        @DisplayName("Does getInfantryUnits return all infantry units in list")
        public void doesGetInfantryUnitsReturnAllInfantryUnitsInList(){
            List<Unit> units = new ArrayList<>();
            units.add(new InfantryUnit("Swordsman",100));
            units.add(new CavalryUnit("Cavalry",100));
            units.add(new RangedUnit("Archer",100));
            units.add(new InfantryUnit("Halberdier",100));
            units.add(new CommanderUnit("Commander",180));
            Army army = new Army("Army",units);

            assertEquals(2, army.getInfantryUnits().size());
        }

        @Test
        @DisplayName("Does getCavalryUnits return only cavalry units and not CommanderUnits")
        public void doesGetCavalryUnitsReturnOnlyCavalryUnitsAndNotCommanderUnits(){
            List<Unit> units = new ArrayList<>();
            units.add(new CommanderUnit("Commander",100));
            units.add(new InfantryUnit("Swordsman",100));
            units.add(new CavalryUnit("Cavalry",100));
            units.add(new CavalryUnit("Cavalry",100));
            units.add(new RangedUnit("Archer",100));
            units.add(new CavalryUnit("Halberdier",100));
            units.add(new CommanderUnit("Commander",180));
            Army army = new Army("Army",units);

            assertEquals(3, army.getCavalryUnits().size());
        }

        @Test
        @DisplayName("Does getRangedUnits return correct number of ranged units")
        public void doesGetRangedUnitsReturnCorrectNumberOfRangedUnits(){
            List<Unit> units = new ArrayList<>();
            units.add(new CommanderUnit("Commander",100));
            units.add(new RangedUnit("Archer",100));
            units.add(new RangedUnit("Archer",100));
            units.add(new CavalryUnit("Cavalry",100));
            units.add(new RangedUnit("Archer",100));
            units.add(new CavalryUnit("Halberdier",100));
            units.add(new RangedUnit("Archer",180));
            Army army = new Army("Army",units);

            assertEquals(4, army.getRangedUnits().size());
        }

        @Test
        @DisplayName("Does getCommanderUnits return correct number of commander units")
        public void doesGetCommanderUnitsReturnCorrectNumberOfCommanderUnits(){
            List<Unit> units = new ArrayList<>();
            units.add(new CommanderUnit("Commander",100));
            units.add(new InfantryUnit("Swordsman",100));
            units.add(new CavalryUnit("Cavalry",100));
            units.add(new CavalryUnit("Cavalry",100));
            units.add(new RangedUnit("Archer",100));
            units.add(new CavalryUnit("Halberdier",100));
            units.add(new InfantryUnit("Swordsman",180));
            Army army = new Army("Army",units);

            assertEquals(1, army.getCommanderUnits().size());
        }
    }
}