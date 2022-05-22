package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangedUnitTest {

    @Test
    @DisplayName("Does the resist bonus for RangedUnit return different values depending on number of calls")
    public void doesResistBonusForRangedUnitReturnDifferentValuesDependingOnNumberOfCalls(){
        RangedUnit rangedUnit = new RangedUnit("Archer", 100);
        int resistBonusFirstCall = rangedUnit.getResistBonus(TerrainType.PLAINS);
        int resistBonusSecondCall = rangedUnit.getResistBonus(TerrainType.PLAINS);
        int resistBonusThirdCall = rangedUnit.getResistBonus(TerrainType.PLAINS);
        int resistBonusFourthCall = rangedUnit.getResistBonus(TerrainType.PLAINS);

        boolean correct = false;
        if (resistBonusThirdCall == resistBonusFourthCall && resistBonusThirdCall == 2){
            if (resistBonusSecondCall == 4){
                if (resistBonusFirstCall == 6){
                    correct = true;
                }
            }
        }
        assertTrue(correct);
    }


    @Test
    @DisplayName("Does RangedUnit get increased Terrain attack bonus on Hill")
    void doesRangedUnitGetIncreasedTerrainAttackBonusOnHill() {
        RangedUnit rangedUnit = new RangedUnit("Ranged",100);

        assertEquals(2, rangedUnit.getTerrainAttackBonus(TerrainType.HILL));
    }

    @Test
    @DisplayName("Does RangedUnit get decreased Terrain attack bonus in Forest")
    void doesRangedUnitGetDecreasedTerrainAttackBonusInForest() {
        RangedUnit rangedUnit = new RangedUnit("Ranged",100);

        assertEquals(-1, rangedUnit.getTerrainAttackBonus(TerrainType.FOREST));
    }

    @Test
    @DisplayName("Does RangedUnit get increased damage bonus when attacking a SpearFighterUnit")
    void doesRangedUnitGetIncreasedDamageBonusWhenAttackingASpearFighterUnit(){
        RangedUnit rangedUnit = new RangedUnit("Ranged",100);

        assertEquals(2, rangedUnit.getOpponentTypeBonus(UnitType.SPEAR_FIGHTER_UNIT));
    }

    @Test
    @DisplayName("Does RangedUnit get increased total attack bonus on Hill against AxeManUnit")
    void doesRangedUnitGetIncreasedTotalAttackBonusOnHillAgainstAxeMan(){
        RangedUnit rangedUnit = new RangedUnit("Ranged", 100);

        assertEquals(7, rangedUnit.getAttackBonus(TerrainType.HILL, UnitType.AXEMAN_UNIT));
    }
}