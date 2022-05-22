package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfantryUnitTest {
    @Test
    @DisplayName("Does InfantryUnit get increased Terrain attack bonus in Forest")
    public void doesInfantryUnitGetIncreasedTerrainAttackBonusInForest() {
        InfantryUnit infantryUnit = new SpearFighterUnit("Spear fighter",100);

        assertEquals(3, infantryUnit.getTerrainAttackBonus(TerrainType.FOREST));
    }

    @Test
    @DisplayName("Does InfantryUnit get increased Terrain defense bonus in Forest")
    public void doesInfantryUnitGetIncreasedTerrainDefenseBonusInForest() {
        InfantryUnit infantryUnit = new AxemanUnit("Axeman",100);

        assertEquals(2, infantryUnit.getTerrainDefenseBonus(TerrainType.FOREST));
    }

}