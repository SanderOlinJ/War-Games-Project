package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses;

/**
 * Interface class TerrainImpactsDefense, used for Units who
 * have advantages in certain TerrainTypes while defending from an attack.
 */
public interface TerrainImpactsDefense {

    /**
     * Method retrieves the TerrainBonus a Unit receives in a certain TerrainType,
     * while defending from an attack.
     * @param terrainType TerrainType, determines the defense bonus outcome.
     * @return Terrain defense bonus of the Unit.
     */
    int getTerrainDefenseBonus(TerrainType terrainType);
}
