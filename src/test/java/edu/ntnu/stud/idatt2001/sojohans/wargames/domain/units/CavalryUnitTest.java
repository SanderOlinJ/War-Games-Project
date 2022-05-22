package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;
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

        // Cavalry should get the same attack bonus after it has been called once.
        if (attackBonusSecondCall == attackBonusThirdCall && attackBonusSecondCall == 2){
            assertEquals(6, attackBonusFirstCall);
        } else {
            fail();
        }
    }

    @Test
    @DisplayName("Does CavalryUnit get increased attack bonus on Plain")
    void doesCavalryUnitGetIncreasedTerrainAttackBonusOnPlains() {
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);

        assertEquals(2, cavalryUnit.getTerrainAttackBonus(TerrainType.PLAINS));
    }

    @Test
    @DisplayName("Does CavalryUnit get no resist bonus in Forest")
    void doesCavalryUnitGetNoResistBonusInForest() {
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);

        assertEquals(0, cavalryUnit.getResistBonus(TerrainType.FOREST));
    }

    @Test
    @DisplayName("Does CavalryUnit get increased damage bonus when attacking another CavalryUnit")
    void doesCavalryUnitGetIncreasedDamageBonusWhenAttackingAnotherCavalryUnit(){
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);

        assertEquals(1, cavalryUnit.getOpponentTypeBonus(UnitType.CAVALRY_UNIT));
    }

    @Test
    @DisplayName("Does CavalryUnit get increased total attack bonus on Plains against AxemanUnit")
    void doesCavalryUnitGetIncreasedTotalAttackBonusOnPlainsAgainstAxeman(){
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry", 100);

        assertEquals(9, cavalryUnit.getAttackBonus(TerrainType.PLAINS, UnitType.AXEMAN_UNIT));
    }
}