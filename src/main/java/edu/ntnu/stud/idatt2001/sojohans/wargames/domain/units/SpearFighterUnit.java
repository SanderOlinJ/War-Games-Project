package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;

/**
 * Class for describing a SpearFighterUnit, a type of InfantryUnit.
 */
public class SpearFighterUnit extends InfantryUnit{

    /**
     * The default constructor for instantiating a SpearFighterUnit.
     * Sets the UnitType attribute of the SpearFighterUnit to 'SPEAR_FIGHTER_UNIT'.
     * @param name Name of the SpearFighterUnit.
     * @param health Health of the SpearFighterUnit, cannot be less than or equal to zero.
     * @param attack Attack-stat of the SpearFighterUnit, cannot be less than or equal to zero.
     * @param armor Armor-stat of the SpearFighterUnit, cannot be less than zero.
     */
    public SpearFighterUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        setUnitType(UnitType.SPEAR_FIGHTER_UNIT);
    }

    /**
     * The simplified constructor for instantiating a SpearFighterUnit.
     * The SpearFighterUnit's attack and armor-stat are set to its default (15 and 10).
     * Sets the UnitType attribute of the SpearFighterUnit to 'SPEAR_FIGHTER_UNIT'.
     * @param name Name of the SpearFighterUnit.
     * @param health Health of the SpearFighterUnit, cannot be less than or equal to zero.
     */
    public SpearFighterUnit(String name, int health){
        super(name, health);
        setUnitType(UnitType.SPEAR_FIGHTER_UNIT);
    }

    /**
     * Method for retrieving the SpearFighterUnit's bonus against certain UnitTypes.
     * SpearFighterUnit has increased attack bonus against CavalryUnits and CommanderUnits.
     * @param unitType UnitType, determines the bonus outcome.
     * @return Bonus of the SpearFighterUnit impacted by the UnitType argument.
     */
    @Override
    public int getOpponentTypeBonus(UnitType unitType) {
        return switch (unitType){
            case CAVALRY_UNIT, COMMANDER_UNIT -> 5;
            default -> 0;
        };
    }
}
