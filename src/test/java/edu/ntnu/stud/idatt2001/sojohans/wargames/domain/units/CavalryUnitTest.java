package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CavalryUnitTest {

    @Test
    @DisplayName("Does getAttackBonus() for CavalryUnit return correct values depending on number of calls to method")
    public void doesGetAttackBonusForCavalryUnitReturnCorrectValuesDependingOnNumberOfCalls(){
        CavalryUnit lightCavalry = new CavalryUnit("Light Cavalry", 100);
        assertEquals(6, lightCavalry.getAttackBonus(TerrainType.HILL));
        assertEquals(2, lightCavalry.getAttackBonus(TerrainType.HILL));
        assertEquals(2, lightCavalry.getAttackBonus(TerrainType.HILL));
    }

    @Test
    void doesCavalryUnitGetIncreasedAttackBonusOnPlains() {
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);

        assertEquals(9, cavalryUnit.getAttackBonus(TerrainType.PLAINS));
    }

    @Test
    void doesCavalryUnitGetNoAttackBonusInForest() {
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);

        assertEquals(0, cavalryUnit.getResistBonus(TerrainType.FOREST));
    }
}