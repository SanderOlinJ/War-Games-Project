package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangedUnitTest {

    @Test
    @DisplayName("Does getResistBonus() return correct values depending on number of calls to method")
    public void doesGetResistBonusReturnCorrectValuesDependingOnNumberOfCalls(){
        RangedUnit archer = new RangedUnit("Archer",100);
        assertEquals(6,archer.getResistBonus(TerrainType.PLAINS));
        assertEquals(4,archer.getResistBonus(TerrainType.PLAINS));
        assertEquals(2,archer.getResistBonus(TerrainType.PLAINS));
        assertEquals(2,archer.getResistBonus(TerrainType.PLAINS));
    }

    @Test
    void doesRangedUnitGetIncreasedAttackBonusOnHill() {
        RangedUnit rangedUnit = new RangedUnit("Ranged",100);

        assertEquals(6, rangedUnit.getAttackBonus(TerrainType.HILL));
    }

    @Test
    void doesRangedUnitGetDecreasedAttackBonusInForest() {
        RangedUnit rangedUnit = new RangedUnit("Ranged",100);

        assertEquals(1, rangedUnit.getAttackBonus(TerrainType.FOREST));
    }
}