package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.TerrainException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Nested
    class InvalidInstantiationOfBattle{

        @Test
        @DisplayName("Instantiate Battle when Army is null")
        public void instantiateBattleWhenArmyIsNull(){
            Army armyOne = new Army("Army One");

            assertThrows(IllegalArgumentException.class, () -> new Battle(armyOne, null, TerrainType.FOREST));
        }

        @Test
        @DisplayName("Instantiate Battle when Army has no units")
        public void instantiateBattleWhenArmyHasNoUnits(){
            Army armyOne = new Army("Army One");
            Army armyTwo = new Army("Army Two");

            assertThrows(IllegalArgumentException.class, () -> new Battle(armyOne, armyTwo, TerrainType.FOREST));
        }

        @Test
        @DisplayName("Instantiate Battle when TerrainType is null")
        public void instantiateBattleWhenTerrainTypeIsNull(){
            Army armyOne = new Army("Army One");
            Army armyTwo = new Army("Army Two");
            armyOne.addUnit(new SpearFighterUnit("Spear fighter",100));
            armyTwo.addUnit(new SpearFighterUnit("Spear fighter",100));

            assertThrows(TerrainException.class, () -> new Battle(armyOne, armyTwo, null));
        }
    }

    @Nested
    class SimulateMethodTestCases{

        @Test
        @DisplayName("Battle simulation when one army has no units")
        public void battleSimulationWhenOneArmyHasNoUnits(){
            ArrayList<Unit> units = new ArrayList<>();
            units.add(new CavalryUnit("Knight",100));
            units.add(new CavalryUnit("Light Cavalry",100));

            Army armyOne = new Army("Army One", units);
            Army armyTwo = new Army("Army Two", units);
            Battle battle = new Battle(armyOne, armyTwo, TerrainType.FOREST);

            for (Unit unit : units){
                armyOne.remove(unit);
            }
            assertFalse(armyOne.hasUnits());

            assertEquals(armyTwo, battle.simulate());
        }
    }
}