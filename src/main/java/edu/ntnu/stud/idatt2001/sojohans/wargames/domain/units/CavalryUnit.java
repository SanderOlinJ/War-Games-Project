package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.TerrainException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitTypeException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.OpponentTypeImpactsBonus;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainImpactsAttack;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainImpactsDefense;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;

/**
 * <p>
 *     Class for describing a CavalryUnit.
 * </p>
 * <p>
 *     CavalryUnit extends from Unit and implements multiple interfaces:
 *     TerrainImpactsAttack, TerrainImpactsDefense, OpponentTypeImpactsBonus
 * </p>
 * <p>
 *     These interfaces impacts the unit's bonuses during an attack, whether it's the Unit
 *     attacking, or the Unit defending.
 * </p>
 */
public class CavalryUnit extends Unit implements TerrainImpactsAttack, TerrainImpactsDefense, OpponentTypeImpactsBonus {

    /**
     * chargedAttack is a variable that tells if the Unit has used its charge attack (the first attack).
     * It is specifically used in the Unit's getAttackBonus method, where it determines how much
     * Attack Bonus it should get.
     */
    private boolean chargedAttack;

    /**
     * <p>
     *     The default constructor for instantiating a CavalryUnit.
     * </p>
     * <p>
     *     Sets the UnitType attribute of the CavalryUnit to 'CAVALRY_UNIT',
     *     and the property 'chargedAttack' to false.
     * </p>
     * @param name Name of the CavalryUnit.
     * @param health Health of the CavalryUnit, cannot be less than or equal to zero.
     * @param attack Attack-stat of the CavalryUnit, cannot be less than or equal to zero.
     * @param armor Armor-stat of the CavalryUnit, cannot be less than zero.
     */
    public CavalryUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        setUnitType(UnitType.CAVALRY_UNIT);
        chargedAttack = false;
    }

    /**
     * <p>
     *     The simplified constructor for instantiating a CavalryUnit.
     * </p>
     * <p>
     *     The CavalryUnit's attack and armor-stat are set to its default (20 and 12).
     * </p>
     * <p>
     *     Sets the UnitType attribute of the CavalryUnit to 'CAVALRY_UNIT',
     *     and the property 'chargedAttack' to false.
     * </p>
     * @param name Name of the CavalryUnit.
     * @param health Health of the CavalryUnit, cannot be less than or equal to zero.
     */
    public CavalryUnit(String name, int health){
        super(name, health, 20, 12);
        setUnitType(UnitType.CAVALRY_UNIT);
        chargedAttack = false;
    }

    /**
     * <p>
     *     Method for retrieving the CavalryUnit's attack bonus.
     * </p>
     * <p>
     *     Attack bonus varies depending on if the unit has used its charged attack.
     * </p>
     * @param terrainType Terrain, may affect attack the bonus outcome.
     * @param unitType UnitType, may affect the attack bonus outcome.
     * @return Attack bonus for CavalryUnit.
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
        int attackBonus = 2;
        if (!this.chargedAttack){
            chargedAttack = true;
            attackBonus = 6;
        }
        return attackBonus + getTerrainAttackBonus(terrainType) + getOpponentTypeBonus(unitType);
    }
    /**
     * Method for retrieving the CavalryUnit's resist bonus.
     * @param terrainType Terrain, may affect the resist bonus outcome.
     * @return Resist bonus of the CavalryUnit.
     * @throws TerrainException If the TerrainType argument is null.
     */
    @Override
    public int getResistBonus(TerrainType terrainType)
    throws TerrainException{
        if (terrainType == null){
            throw new TerrainException("TerrainType cannot be null!");
        }
        return 1 + getTerrainDefenseBonus(terrainType);
    }

    /**
     * <p>
     *     Method for retrieving the CavalryUnit's terrain bonus during attack.
     * </p>
     * <p>
     *     CavalryUnit only get Terrain attack bonus on the TerrainType 'PLAINS'.
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
        }
        if (terrainType.equals(TerrainType.PLAINS)){
            return 2;
        }
        return 0;
    }

    /**
     * <p>
     *     Method for retrieving the CavalryUnit's terrain bonus during defense.
     * </p>
     * <p>
     *     CavalryUnit gets a decreased defense bonus in the TerrainType 'FOREST'.
     * </p>
     * @param terrainType Terrain, determines the defense bonus outcome.
     * @return Terrain defense bonus of the CavalryUnit.
     * @throws TerrainException If the TerrainType argument is null.
     */
    @Override
    public int getTerrainDefenseBonus(TerrainType terrainType)
    throws TerrainException{
        if (terrainType == null){
            throw new TerrainException("TerrainType cannot be null!");
        }
        if (terrainType.equals(TerrainType.FOREST)){
            return -1;
        }
        return 0;
    }

    /**
     * <p>
     *     Method for retrieving the CavalryUnit's bonus against certain UnitTypes.
     * </p>
     * <p>
     *     CavalryUnit has increased attack bonus against AxemanUnits, CommanderUnits
     *     and other CavalryUnits.
     * </p>
     * @param unitType UnitType, determines the bonus outcome.
     * @return Bonus of the CavalryUnit impacted by the UnitType argument.
     */
    @Override
    public int getOpponentTypeBonus(UnitType unitType) {
        return switch (unitType){
            case AXEMAN_UNIT, CAVALRY_UNIT, COMMANDER_UNIT -> 1;
            default -> 0;
        };
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
