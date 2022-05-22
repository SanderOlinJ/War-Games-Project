package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;

/**
 * Class for describing a SwordsmanUnit, a type of InfantryUnit.
 */
public class SwordsmanUnit extends InfantryUnit{

    /**
     * <p>
     *     The default constructor for instantiating a SwordsmanUnit.
     * </p>
     * <p>
     *     Sets the UnitType attribute of the SwordsmanUnit to 'SWORDSMAN_UNIT'.
     * </p>
     * @param name Name of the SwordsmanUnit.
     * @param health Health of the SwordsmanUnit, cannot be less than or equal to zero.
     * @param attack Attack-stat of the SwordsmanUnit, cannot be less than or equal to zero.
     * @param armor Armor-stat of the SwordsmanUnit, cannot be less than zero.
     */
    public SwordsmanUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        setUnitType(UnitType.SWORDSMAN_UNIT);
    }

    /**
     * <p>
     *     The simplified constructor for instantiating a SwordsmanUnit.
     * </p>
     * <p>
     *     The SwordsmanUnit's attack and armor-stat are set to its default (15 and 10).
     * </p>
     * <p>
     *     Sets the UnitType attribute of the SwordsmanUnit to 'SWORDSMAN_UNIT'.
     * </p>
     * @param name Name of the SwordsmanUnit.
     * @param health Health of the SwordsmanUnit, cannot be less than or equal to zero.
     */
    public SwordsmanUnit(String name, int health){
        super(name, health, 15, 10);
        setUnitType(UnitType.SWORDSMAN_UNIT);
    }

    /**
     * <p>
     *     Method for retrieving the SwordsmanUnit's bonus against certain UnitTypes.
     * </p>
     * <p>
     *     SwordsmanUnit has increased attack bonus against SpearFighterUnits, RangedUnits
     *     and other SwordsmanUnits.
     * </p>
     * @param unitType UnitType, determines the bonus outcome.
     * @return Bonus of the SwordsmanUnit impacted by the UnitType argument.
     */
    @Override
    public int getOpponentTypeBonus(UnitType unitType) {
        return switch (unitType){
            case SPEAR_FIGHTER_UNIT, SWORDSMAN_UNIT, RANGED_UNIT -> 1;
            default -> 0;
        };
    }
}
