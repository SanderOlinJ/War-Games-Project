package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainImpactsAttack;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainImpactsDefense;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;

/**
 * Class for describing a InfantryUnit.
 */
public class InfantryUnit extends Unit implements TerrainImpactsAttack, TerrainImpactsDefense {

    /**
     * The default constructor for InfantryUnit.
     * @param name Name of the InfantryUnit.
     * @param health Health of the InfantryUnit, cannot be less than or equal to 0.
     * @param attack Attack-stat of the InfantryUnit, cannot be less than or equal to 0.
     * @param armor Armor-stat of the InfantryUnit, cannot be less than 0.
     */
    public InfantryUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
    }

    /**
     * The simplified constructor for InfantryUnit.
     * The InfantryUnit's attack and armor-stat are set to its default (15 and 10).
     * @param name Name of the InfantryUnit.
     * @param health Health of the InfantryUnit, cannot be less than or equal to 0.
     */
    public InfantryUnit(String name, int health){
        super(name, health, 15, 10);
    }

    /**
     * Method for retrieving the InfantryUnit's attack bonus.
     * @param terrainType Terrain, affects the bonus outcome.
     * @return Attack bonus of the InfantryUnit.
     */
    @Override
    public int getAttackBonus(TerrainType terrainType) {
        return 2 + getTerrainAttackBonus(terrainType);
    }

    /**
     * Method for retrieving the InfantryUnit's resist bonus.
     * @param terrainType Terrain, affects the bonus outcome.
     * @return Resist bonus of the InfantryUnit.
     */
    @Override
    public int getResistBonus(TerrainType terrainType) {
        return 1 + getTerrainDefenseBonus(terrainType);
    }

    /**
     * Method for getting InfantryUnit as a String.
     * @return InfantryUnit as String.
     */
    @Override
    public String toString() {
        return "\nInfantry " + super.toString();
    }


    @Override
    public int getTerrainAttackBonus(TerrainType terrainType) {
        if (terrainType == null){
            throw new IllegalArgumentException("TerrainType cannot be null!");
        }
        if (terrainType.equals(TerrainType.FOREST)){
            return 4;
        }
        return 0;
    }


    @Override
    public int getTerrainDefenseBonus(TerrainType terrainType) {
        if (terrainType == null){
            throw new IllegalArgumentException("TerrainType cannot be null!");
        }
        if (terrainType.equals(TerrainType.FOREST)){
            return 2;
        }
        return 0;
    }
}
