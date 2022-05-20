package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;

public class AxemanUnit extends InfantryUnit{

    /**
     * The default constructor for InfantryUnit.
     * @param name Name of the InfantryUnit.
     * @param health Health of the InfantryUnit, cannot be less than or equal to 0.
     * @param attack Attack-stat of the InfantryUnit, cannot be less than or equal to 0.
     * @param armor Armor-stat of the InfantryUnit, cannot be less than 0.
     */
    public AxemanUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        setUnitType(UnitType.AXEMAN_UNIT);
    }

    /**
     * The simplified constructor for InfantryUnit.
     * The InfantryUnit's attack and armor-stat are set to its default (15 and 10).
     * @param name Name of the InfantryUnit.
     * @param health Health of the InfantryUnit, cannot be less than or equal to 0.
     */
    public AxemanUnit(String name, int health){
        super(name, health, 15, 10);
        setUnitType(UnitType.AXEMAN_UNIT);
    }

    @Override
    public int getOpponentTypeBonus(UnitType unitType) {
        return switch (unitType){
            case SPEAR_FIGHTER_UNIT, SWORDSMAN_UNIT, AXEMAN_UNIT, RANGED_UNIT -> 1;
            default -> 0;
        };
    }
}
