package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainImpactsAttack;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainImpactsDefense;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;

/**
 * Class for describing a CavalryUnit.
 */
public class CavalryUnit extends Unit implements TerrainImpactsAttack, TerrainImpactsDefense {

    /**
     * chargedAttack is a variable that tells if the unit has used its charge attack (the first attack).
     * It is specifically used in the unit's getAttackBonus method, where it determines how much
     * Attack Bonus it should get.
     */
    private boolean chargedAttack;

    /**
     * The default constructor for CavalryUnit.
     * @param name Name of the CavalryUnit.
     * @param health Health of the CavalryUnit, cannot be less than or equal to 0.
     * @param attack Attack-stat of the CavalryUnit, cannot be less than or equal to 0.
     * @param armor Armor-stat of the CavalryUnit, cannot be less than 0.
     */
    public CavalryUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        chargedAttack = false;
    }

    /**
     * The simplified constructor for CavalryUnit.
     * The CavalryUnit's attack and armor-stat are set to its default (20 and 12).
     * @param name Name of the CavalryUnit.
     * @param health Health of the CavalryUnit, cannot be less than or equal to 0.
     */
    public CavalryUnit(String name, int health){
        super(name, health, 20, 12);
        chargedAttack = false;
    }

    /**
     * Method for retrieving the CavalryUnit's attack bonus.
     * Varies depending on if the unit has used its charged attack.
     * @param terrainType Terrain, affects the bonus outcome.
     * @return the Attack bonus for CavalryUnit, 6 and 2.
     */
    @Override
    public int getAttackBonus(TerrainType terrainType) {
        int attackBonus = 2;
        if (!this.chargedAttack){
            chargedAttack = true;
            attackBonus = 6;
        }
        return attackBonus + getTerrainAttackBonus(terrainType);
    }
    /**
     * Method for retrieving the CavalryUnit's resist bonus.
     * @param terrainType Terrain, affects the bonus outcome.
     * @return Resist bonus of the CavalryUnit.
     */
    @Override
    public int getResistBonus(TerrainType terrainType) {
        return 1 + getTerrainDefenseBonus(terrainType);
    }

    @Override
    public int getTerrainAttackBonus(TerrainType terrainType) {
        if (terrainType == null){
            throw new IllegalArgumentException("TerrainType cannot be null!");
        }
        if (terrainType.equals(TerrainType.PLAINS)){
            return 3;
        }
        return 0;
    }

    @Override
    public int getTerrainDefenseBonus(TerrainType terrainType) {
        if (terrainType == null){
            throw new IllegalArgumentException("TerrainType cannot be null!");
        }
        if (terrainType.equals(TerrainType.FOREST)){
            return -1;
        }
        return 0;
    }

    /**
     * Method for getting CavalryUnit as a String.
     * @return CavalryUnit as String.
     */
    @Override
    public String toString() {
        return "\nCavalry " + super.toString();
    }

}
