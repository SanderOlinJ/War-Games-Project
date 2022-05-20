package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
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
    void doesRangedUnitGetIncreasedAttackBonusOnHill() {
        RangedUnit rangedUnit = new RangedUnit("Ranged",100);

        assertEquals(6, rangedUnit.getAttackBonus(TerrainType.HILL, UnitType.SPEAR_FIGHTER_UNIT));
    }

    @Test
    void doesRangedUnitGetDecreasedAttackBonusInForest() {
        RangedUnit rangedUnit = new RangedUnit("Ranged",100);

        assertEquals(1, rangedUnit.getAttackBonus(TerrainType.FOREST, UnitType.SPEAR_FIGHTER_UNIT));
    }
}