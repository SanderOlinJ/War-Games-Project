package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.TerrainException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitTypeException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.OpponentTypeImpactsBonus;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainImpactsAttack;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;

/**
 * <p>
 *     Class for describing a RangedUnit.
 * </p>
 * <p>
 *     RangedUnit extends from Unit and implements multiple interfaces:
 *     TerrainImpactsAttack, OpponentTypeImpactsBonus
 * </p>
 * <p>
 *     These interfaces impacts the unit's bonuses during an attack, but only
 *     when it's the attacker.
 * </p>
 */
public class RangedUnit extends Unit implements TerrainImpactsAttack, OpponentTypeImpactsBonus {

    /**
     * numberOfAttacksWithstood is a variable that counts how many times this Unit has been attacked
     * It is specifically used in the Unit's getResistBonus method, where it determines how much
     * Resist Bonus it should get.
     */
    private int numberOfAttacksWithstood;

    /**
     * <p>
     *     The default constructor for instantiating a RangedUnit.
     * </p>
     * <p>
     *     Sets the UnitType attribute of the RangedUnit to 'RANGED_UNIT',
     *     and the property 'numberOfAttacksWithstood' to zero.
     * </p>
     * @param name Name of the RangedUnit.
     * @param health Health of the RangedUnit, cannot be less than or equal to zero.
     * @param attack Attack-stat of the RangedUnit, cannot be less than or equal to zero.
     * @param armor Armor-stat of the RangedUnit, cannot be less than zero.
     */
    public RangedUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        setUnitType(UnitType.RANGED_UNIT);
        numberOfAttacksWithstood = 0;
    }

    /**
     * <p>
     *     The simplified constructor for instantiating a RangedUnit.
     * </p>
     * <p>
     *     The RangedUnit's attack and armor-stat are set to its default (15 and 8).
     * </p>
     * <p>
     *     Sets the UnitType attribute of the RangedUnit to 'RANGED_UNIT',
     *     and the property 'numberOfAttacksWithstood' to zero.
     * </p>
     * @param name Name of the RangedUnit.
     * @param health Health of the RangedUnit, cannot be less than or equal to zero.
     */
    public RangedUnit(String name, int health){
        super(name, health, 15, 8);
        setUnitType(UnitType.RANGED_UNIT);
        numberOfAttacksWithstood = 0;
    }

    /**
     * Method for retrieving the RangedUnit's attack bonus.
     * @param terrainType Terrain, may affect the attack bonus outcome.
     * @param unitType UnitType, may affect the attack bonus outcome.
     * @return Attack bonus of the RangedUnit.
     * @throws TerrainException If the TerrainType argument is null.
     * @throws UnitTypeException If the UnitType argument is null.
     */
    @Override
    public int getAttackBonus(TerrainType terrainType, UnitType unitType)
    throws TerrainException, UnitTypeException{
        if (terrainType == null){
            throw new TerrainException("TerrainType cannot be null!");
        }
        if (unitType == null){
            throw new UnitTypeException("UnitType cannot be null!");
        }
        return 3 + getTerrainAttackBonus(terrainType) + getOpponentTypeBonus(unitType);
    }

    /**
     * <p>
     *     Method for retrieving the RangedUnit's resist bonus.
     * </p>
     * <p>
     *     Resist bonus varies depending on how many times it has been attacked.
     * </p>
     * @param terrainType Terrain, doesn't affect resist bonus outcome for RangedUnit.
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

    /**
     * <p>
     *     Method for retrieving the RangedUnit's terrain bonus during attack.
     * </p>
     * <p>
     *     RangedUnit only get Terrain attack bonus on the TerrainType 'HILL'.
     * </p>
     * <p>
     *     It however gets a decreased bonus in the TerrainType 'FOREST'.
     * </p>
     * @param terrainType Terrain, determines the attack bonus outcome.
     * @return Terrain attack bonus of the RangedUnit.
     * @throws TerrainException If the TerrainType argument is null.
     */
    @Override
    public int getTerrainAttackBonus(TerrainType terrainType)
    throws TerrainException{
        if (terrainType == null){
            throw new TerrainException("TerrainType cannot be null!");
        } if (terrainType.equals(TerrainType.HILL)){
            return 2;
        } else if (terrainType.equals(TerrainType.FOREST)){
            return -1;
        }
        return 0;
    }

    /**
     * <p>
     *     Method for retrieving the RangedUnit's bonus against certain UnitTypes.
     * </p>
     * <p>
     *     RangedUnit has increased attack bonus against SwordsmanUnits, AxemanUnits
     *     and SpearFighterUnits.
     * </p>
     * @param unitType UnitType, determines the bonus outcome.
     * @return Bonus of the RangedUnit impacted by the UnitType argument.
     */
    @Override
    public int getOpponentTypeBonus(UnitType unitType) {
        return switch (unitType){
            case SWORDSMAN_UNIT, AXEMAN_UNIT, SPEAR_FIGHTER_UNIT -> 2;
            default -> 0;
        };
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
