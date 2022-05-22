package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.TerrainException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitTypeException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.OpponentTypeImpactsBonus;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainImpactsAttack;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainImpactsDefense;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;

/**
 * Class for describing an abstract InfantryUnit.
 * InfantryUnit extends from Unit and implements multiple interfaces:
 * TerrainTypeImpactsAttack, TerrainTypeImpactsDefense, OpponentTypeImpactsBonus
 * These interfaces impacts the unit's bonuses during an attack, whether it's the Unit
 * attacking, or the Unit defending.
 */
public abstract class InfantryUnit extends Unit implements TerrainImpactsAttack, TerrainImpactsDefense,
        OpponentTypeImpactsBonus {

    /**
     * The default constructor for instantiating an InfantryUnit.
     * @param name Name of the InfantryUnit.
     * @param health Health of the InfantryUnit, cannot be less than or equal to zero.
     * @param attack Attack-stat of the InfantryUnit, cannot be less than or equal to zero.
     * @param armor Armor-stat of the InfantryUnit, cannot be less than zero.
     */
    public InfantryUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
    }

    /**
     * The simplified constructor for instantiating an InfantryUnit.
     * The InfantryUnit's attack and armor-stat are set to its default (15 and 10).
     * @param name Name of the InfantryUnit.
     * @param health Health of the InfantryUnit, cannot be less than or equal to zero.
     */
    public InfantryUnit(String name, int health){
        super(name, health, 15, 10);
    }

    /**
     * Method for retrieving the InfantryUnit's attack bonus.
     * @param terrainType Terrain, may affect the attack bonus outcome.
     * @param unitType UnitType, may affect the attack bonus outcome.
     * @return Attack bonus of the InfantryUnit.
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
        return 2 + getTerrainAttackBonus(terrainType) + getOpponentTypeBonus(unitType);
    }

    /**
     * Method for retrieving the InfantryUnit's resist bonus.
     * @param terrainType Terrain, may affect the resist bonus outcome.
     * @return Resist bonus of the InfantryUnit.
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
     * Method for retrieving the InfantryUnit's terrain bonus during attack.
     * InfantryUnit only get Terrain attack bonus in the TerrainType 'FOREST'.
     * @param terrainType Terrain, determines the attack bonus outcome.
     * @return Terrain attack bonus of the InfantryUnit.
     * @throws TerrainException If the TerrainType argument is null.
     */
    @Override
    public int getTerrainAttackBonus(TerrainType terrainType)
    throws TerrainException{
        if (terrainType == null){
            throw new TerrainException("TerrainType cannot be null!");
        }
        if (terrainType.equals(TerrainType.FOREST)){
            return 3;
        }
        return 0;
    }

    /**
     * Method for retrieving the InfantryUnit's terrain bonus during defense.
     * InfantryUnit only get Terrain defense bonus in the TerrainType 'FOREST'.
     * @param terrainType Terrain, determines the defense bonus outcome.
     * @return Terrain defense bonus of the InfantryUnit.
     * @throws TerrainException If the TerrainType argument is null.
     */
    @Override
    public int getTerrainDefenseBonus(TerrainType terrainType)
    throws TerrainException{
        if (terrainType == null){
            throw new TerrainException("TerrainType cannot be null!");
        }
        if (terrainType.equals(TerrainType.FOREST)){
            return 2;
        }
        return 0;
    }

    /**
     * Method for retrieving the InfantryUnit's bonus against certain UnitTypes.
     * @param unitType UnitType, determines the bonus outcome.
     * @return Bonus of the InfantryUnit impacted by the UnitType argument
     */
    @Override
    public abstract int getOpponentTypeBonus(UnitType unitType);

    /**
     * Method for getting InfantryUnit as a String.
     * @return InfantryUnit as String.
     */
    @Override
    public String toString() {
        return "\nInfantry " + super.toString();
    }
}
