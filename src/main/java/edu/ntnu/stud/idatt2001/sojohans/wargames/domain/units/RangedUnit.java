package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.OpponentTypeImpactsBonus;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainImpactsAttack;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;

/**
 * Class for describing a RangedUnit.
 */
public class RangedUnit extends Unit implements TerrainImpactsAttack, OpponentTypeImpactsBonus {

    /**
     * numberOfAttacksWithstood is a variable that counts how many times this unit has been attacked
     * It is specifically used in the unit's getResistBonus method, where it determines how much
     * Resist Bonus it should get.
     */
    private int numberOfAttacksWithstood;

    /**
     * The default constructor for RangedUnit.
     * @param name Name of the RangedUnit.
     * @param health Health of the RangedUnit, cannot be less than or equal to 0.
     * @param attack Attack-stat of the RangedUnit, cannot be less than or equal to 0.
     * @param armor Armor-stat of the RangedUnit, cannot be less than 0.
     */
    public RangedUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        setUnitType(UnitType.RANGED_UNIT);
        numberOfAttacksWithstood = 0;
    }

    /**
     * The simplified constructor for RangedUnit.
     * The RangedUnit's attack and armor-stat are set to its default (15 and 8).
     * @param name Name of the RangedUnit.
     * @param health Health of the RangedUnit, cannot be less than or equal to 0.
     */
    public RangedUnit(String name, int health){
        super(name, health, 15, 8);
        setUnitType(UnitType.RANGED_UNIT);
        numberOfAttacksWithstood = 0;
    }

    /**
     * Method for retrieving the RangedUnit's attack bonus.
     * @param terrainType Terrain, affects the bonus outcome.
     * @return Attack bonus of the RangedUnit.
     */
    @Override
    public int getAttackBonus(TerrainType terrainType, UnitType unitType) {
        return 3 + getTerrainAttackBonus(terrainType) + getOpponentTypeBonus(unitType);
    }

    /**
     * Method for retrieving the RangedUnit's resist bonus.
     * @param terrainType Terrain, doesn't affect ResistBonus.
     * @return Resist bonus of the RangedUnit.
     */
    @Override
    public int getResistBonus(TerrainType terrainType) {
        if (this.numberOfAttacksWithstood == 0){
            this.numberOfAttacksWithstood++;
            return 6;
        }else if(this.numberOfAttacksWithstood == 1){
            this.numberOfAttacksWithstood++;
            return 4;
        }else {
            return 2;
        }
    }

    @Override
    public int getOpponentTypeBonus(UnitType unitType) {
        return switch (unitType){
            case SWORDSMAN_UNIT, AXEMAN_UNIT, SPEAR_FIGHTER_UNIT -> 2;
            default -> 0;
        };
    }

    @Override
    public int getTerrainAttackBonus(TerrainType terrainType) {
        if (terrainType == null){
            throw new IllegalArgumentException("TerrainType cannot be null!");
        } if (terrainType.equals(TerrainType.HILL)){
            return 2;
        } else if (terrainType.equals(TerrainType.FOREST)){
            return -1;
        }
        return 0;
    }

    /**
     * Method for getting RangedUnit as a String.
     * @return RangedUnit as String.
     */
    @Override
    public String toString() {
        return "\nRanged " + super.toString();
    }
}
