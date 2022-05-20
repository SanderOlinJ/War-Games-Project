package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CavalryUnitTest {


    @Test
    @DisplayName("Does the attack bonus for CavalryUnit return different values depending on number of calls")
    public void doestAttackBonusForCavalryUnitReturnDifferentValuesDependingOnNumberOfCalls(){
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);
        //Cavalry does not get any attack bonus on hill or against ranged unit.
        int attackBonusFirstCall = cavalryUnit.getAttackBonus(TerrainType.HILL, UnitType.RANGED_UNIT);
        int attackBonusSecondCall = cavalryUnit.getAttackBonus(TerrainType.HILL, UnitType.RANGED_UNIT);
        int attackBonusThirdCall = cavalryUnit.getAttackBonus(TerrainType.HILL, UnitType.RANGED_UNIT);

        // Cavalry should get the same attack bonus after its been called more than 0.
        if (attackBonusSecondCall == attackBonusThirdCall && attackBonusSecondCall == 2){
            assertEquals(6, attackBonusFirstCall);
        } else {
            fail();
        }
    }

    @Test
    void doesCavalryUnitGetIncreasedAttackBonusOnPlains() {
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);

        assertEquals(9, cavalryUnit.getAttackBonus(TerrainType.PLAINS, UnitType.RANGED_UNIT));
    }

    @Test
    void doesCavalryUnitGetNoAttackBonusInForest() {
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);

        assertEquals(0, cavalryUnit.getResistBonus(TerrainType.FOREST));
    }

    @Test
    public void doesAttackReturnCorrectHealthValueWhenCavalryAttackSpearFighterInForest(){
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry", 100);

        assertEquals(-5, cavalryUnit.getOpponentTypeBonus(UnitType.SPEAR_FIGHTER_UNIT));

    }
}