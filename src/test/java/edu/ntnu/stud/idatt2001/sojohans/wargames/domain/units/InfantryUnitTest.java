package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfantryUnitTest {
    @Test
    public void doesInfantryUnitGetIncreasedAttackBonusInForest() {
        InfantryUnit infantryUnit = new SpearFighterUnit("Spear fighter",100);

        assertEquals(6, infantryUnit.getAttackBonus(TerrainType.FOREST, UnitType.SPEAR_FIGHTER_UNIT));
    }

    @Test
    public void doesInfantryUnitGetIncreasedResistBonusInForest() {
        InfantryUnit infantryUnit = new AxemanUnit("Axeman",100);

        assertEquals(3, infantryUnit.getResistBonus(TerrainType.FOREST));
    }

}