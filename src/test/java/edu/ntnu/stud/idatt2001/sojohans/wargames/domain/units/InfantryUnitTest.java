package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfantryUnitTest {
    @Test
    void doesInfantryUnitGetIncreasedAttackBonusInForest() {
        InfantryUnit infantryUnit = new InfantryUnit("Infantry",100);

        assertEquals(6, infantryUnit.getAttackBonus(TerrainType.FOREST));
    }

    @Test
    void doesInfantryUnitGetIncreasedResistBonusInForest() {
        InfantryUnit infantryUnit = new InfantryUnit("Infantry",100);

        assertEquals(3, infantryUnit.getResistBonus(TerrainType.FOREST));
    }
}