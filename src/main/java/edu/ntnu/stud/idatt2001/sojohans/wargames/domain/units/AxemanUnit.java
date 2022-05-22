package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;

/**
 * Class for describing an AxemanUnit, a type of InfantryUnit.
 */
public class AxemanUnit extends InfantryUnit{

    /**
     * <p>
     *     The default constructor for instantiating an AxemanUnit.
     * </p>
     * <p>
     *     Sets the UnitType attribute of the AxemanUnit to 'AXEMAN_UNIT'.
     * </p>
     * @param name Name of the AxemanUnit.
     * @param health Health of the AxemanUnit, cannot be less than or equal to zero.
     * @param attack Attack-stat of the AxemanUnit, cannot be less than or equal to zero.
     * @param armor Armor-stat of the AxemanUnit, cannot be less than zero.
     */
    public AxemanUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        setUnitType(UnitType.AXEMAN_UNIT);
    }

    /**
     * <p>
     *     The simplified constructor for instantiating an AxemanUnit.
     * </p>
     * <p>
     *     The AxemanUnit's attack and armor-stat are set to its default (15 and 10).
     * </p>
     * <p>
     *     Sets the UnitType attribute of the AxemanUnit to 'AXEMAN_UNIT'.
     * </p>
     * @param name Name of the AxemanUnit.
     * @param health Health of the AxemanUnit, cannot be less than or equal to zero.
     */
    public AxemanUnit(String name, int health){
        super(name, health, 15, 10);
        setUnitType(UnitType.AXEMAN_UNIT);
    }

    /**
     * <p>
     *     Method for retrieving the AxemanUnit's bonus against certain UnitTypes.
     * </p>
     * <p>
     *     AxemanUnit has increased attack bonus against SpearFighterUnits, RangedUnits,
     *     SwordsmanUnits and other AxemanUnits.
     * </p>
     * @param unitType UnitType, determines the bonus outcome.
     * @return Bonus of the AxemanUnit impacted by the UnitType argument.
     */
    @Override
    public int getOpponentTypeBonus(UnitType unitType) {
        return switch (unitType){
            case SPEAR_FIGHTER_UNIT, SWORDSMAN_UNIT, AXEMAN_UNIT, RANGED_UNIT -> 1;
            default -> 0;
        };
    }
}
