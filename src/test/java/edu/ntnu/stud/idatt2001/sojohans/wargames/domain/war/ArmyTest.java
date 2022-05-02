package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {

    @Nested
    class InitiatingArmyClass {

        @Test
        @DisplayName("Initiate Army with an ArrayList of Units")
        public void initiateArmyWithAnArrayListOfUnits(){
            ArrayList<Unit> units = new ArrayList<>();
            units.add(new CommanderUnit("Garen",180));
            units.add(new CavalryUnit("Jarvan",100));
            units.add(new InfantryUnit("Fiora",100));
            Army demacians = new Army("Demacians",units);

            assertEquals(3, demacians.getUnits().size());
        }

        @Test
        @DisplayName("Initiate Army with a null or empty ArrayList")
        public void initiateArmyWithANullOrEmptyArrayList() {
            ArrayList<Unit> emptyUnitsList = new ArrayList<>();
            assertThrows(IllegalArgumentException.class, () -> new Army("Toussaint", emptyUnitsList));

            assertThrows(IllegalArgumentException.class, () -> new Army("Rivia", null));
        }
    }

    @Nested
    class AddingUnitsToArmy {

        @Nested
        class AddingValidUnitsToArmy {

            @Test
            @DisplayName("Check if single Unit is added to Army's ArrayList of Units")
            public void checkIfSingleUnitIsAddedToArmysArrayListOfUnits() {
                Army guardians = new Army("Guardians");
                assertFalse(guardians.hasUnits());

                guardians.addUnit(new CommanderUnit("Cayde-6",180));
                assertTrue(guardians.hasUnits());
            }

            @Test
            @DisplayName("Check if an ArrayList of Units is added to Army's ArrayList of Units")
            public void checkIfAnArrayListOfUnitsIsAddedToArmysArrayListListOfUnits(){
                Army guardians = new Army("Guardians");
                ArrayList<Unit> units = new ArrayList<>();
                units.add(new CommanderUnit("Ikora",180));
                units.add(new CommanderUnit("Zavala",180));
                assertFalse(guardians.hasUnits());

                guardians.addAllUnits(units);
                assertTrue(guardians.hasUnits());
            }

            @Test
            @DisplayName("Check if Units added or instantiated with are in fact deep copied," +
                    "and Army's ArrayList of Units is not coupled to the ArrayList it was instantiated with")
            public void checkIfUnitsAreDeepCopiedAndArmysArrayListIsNotCoupled(){
                ArrayList<Unit> units = new ArrayList<>();
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
            @DisplayName("Adding null and empty ArrayList to Army, throws IllegalArgumentException")
            public void addingNullAndEmptyArrayListToArmyThrowsIllegalArgumentException(){
                Army kaedwen = new Army("Kaedwen");
                assertThrows(IllegalArgumentException.class, () -> kaedwen.addAllUnits(null));

                ArrayList<Unit> emptyUnitList = new ArrayList<>();
                assertThrows(IllegalArgumentException.class, () -> kaedwen.addAllUnits(emptyUnitList));
            }

            @Test
            @DisplayName("Adding ArrayList with a null Unit to Army, throws IllegalArgumentException")
            void addingArrayListWithANullUnitToArmyThrowsIllegalArgumentException(){
                ArrayList<Unit> listWithANullUnit = new ArrayList<>();
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
        ArrayList<Unit> units = new ArrayList<>();
        units.add(new CavalryUnit("Knight 1",100));
        units.add(new CavalryUnit("Knight 2",100));
        Army army = new Army("Army", units);

        Unit randomUnit = army.getRandom();
        assertTrue(army.getUnits().contains(randomUnit));
    }

    @Test
    @DisplayName("Does the returned ArrayList from getUnits() let you change Unit attributes")
    public void doesReturnedArrayListFromGetUnitsLetYouChangeUnitAttributes(){
        Army army = new Army("Army");
        army.addUnit(new InfantryUnit("Swordsman",100));

        ArrayList<Unit> units = army.getUnits();
        units.get(0).setHealth(40);

        assertEquals(40, army.getUnits().get(0).getHealth());
        assertEquals(40, units.get(0).getHealth());
    }

    @Test
    @DisplayName("Does the returned ArrayList from getUnits() stay coupled to Army's private ArrayList")
    public void doesReturnedArrayListFromGetUnitsStayCoupledToArmysPrivateArrayList(){
        Army army = new Army("Army");
        army.addUnit(new InfantryUnit("Swordsman",100));

        ArrayList<Unit> units = army.getUnits();
        units.add(new RangedUnit("Archer",100));

        assertEquals(1, army.getUnits().size());
        assertEquals(2, units.size());
    }

    @Test
    @DisplayName("Test to see if getRandom throws exception if Army's ArrayList of Units is empty")
    public void testToSeeIfGetRandomThrowsExceptionIfArmysArrayListOfUnitsIsEmpty(){
        Army army = new Army("Army");
        assertThrows(IllegalArgumentException.class, army::getRandom);
    }
}