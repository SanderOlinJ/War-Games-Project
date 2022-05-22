package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;


import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;

/**
 * Class for describing a CommanderUnit, an extension of CavalryUnit.
 */
public class CommanderUnit extends CavalryUnit{

    /**
     * <p>
     *     The default constructor for instantiating a CommanderUnit.
     * </p>
     * <p>
     *     Sets the UnitType attribute of the CommanderUnit to 'COMMANDER_UNIT',
     *     as well as the property 'chargedAttack' (inherited from CavalryUnit) to false.
     * </p>
     * @param name Name of the CommanderUnit.
     * @param health Health of the CommanderUnit, cannot be less than or equal to zero.
     * @param attack Attack-stat of the CommanderUnit, cannot be less than or equal to zero.
     * @param armor Armor-stat of the CommanderUnit, cannot be less than zero.
     */
    public CommanderUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        setUnitType(UnitType.COMMANDER_UNIT);
    }

    /**
     * <p>
     *     The simplified constructor for instantiating a CommanderUnit.
     * </p>
     * <p>
     *     The CommanderUnit's attack and armor-stat are set to its default (25 and 15).
     * </p>
     * <p>
     *     Sets the UnitType attribute of the CommanderUnit to 'COMMANDER_UNIT',
     *     as well as the property 'chargedAttack' (inherited from CavalryUnit) to false.
     * </p>
     * @param name Name of the CommanderUnit.
     * @param health Health of the CommanderUnit, cannot be less than or equal to zero.
     */
    public CommanderUnit(String name, int health){
        super(name, health, 25, 15);
        setUnitType(UnitType.COMMANDER_UNIT);
    }

    /**
     * Method for getting CommanderUnit as a String.
     * @return CommanderUnit as String.
     */
    @Override
    public String toString() {
        return "\nCommander " + super.toString();
    }
}
