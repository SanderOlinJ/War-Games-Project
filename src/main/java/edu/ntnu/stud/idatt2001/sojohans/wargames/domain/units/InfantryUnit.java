package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;

/**
 * Class for describing a InfantryUnit.
 */
public class InfantryUnit extends Unit{

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
     * @return Attack bonus of the InfantryUnit.
     */
    @Override
    public int getAttackBonus() {
        int attackBonus = 2;
        if (getTerrainType() == null){
            return attackBonus;
        }
        if (getTerrainType().equals(TerrainType.FOREST)){
            attackBonus += 4;
        }
        return attackBonus;
    }

    /**
     * Method for retrieving the InfantryUnit's resist bonus.
     * @return Resist bonus of the InfantryUnit.
     */
    @Override
    public int getResistBonus() {
        int resistBonus = 1;
        if (getTerrainType() == null){
            return resistBonus;
        }
        if (getTerrainType().equals(TerrainType.FOREST)){
            resistBonus += 2;
        }
        return resistBonus;
    }

    /**
     * Method for getting InfantryUnit as a String.
     * @return InfantryUnit as String.
     */
    @Override
    public String toString() {
        return "\nInfantry " + super.toString();
    }
}
