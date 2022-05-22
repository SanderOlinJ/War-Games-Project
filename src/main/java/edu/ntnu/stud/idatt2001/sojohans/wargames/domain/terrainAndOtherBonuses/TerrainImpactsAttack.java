package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses;

/**
 * Interface class TerrainImpactsAttack, used for Units who
 * have advantages in certain TerrainTypes while attacking.
 */
public interface TerrainImpactsAttack {

    /**
     * Method retrieves the TerrainBonus a Unit receives in a certain TerrainType,
     * while attacking.
     * @param terrainType TerrainType, determines the attack bonus outcome.
     * @return Terrain attack bonus of the Unit.
     */
    int getTerrainAttackBonus(TerrainType terrainType);
}
