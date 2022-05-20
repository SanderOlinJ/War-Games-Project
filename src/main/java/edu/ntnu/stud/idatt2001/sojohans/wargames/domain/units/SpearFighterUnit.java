package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;

public class SpearFighterUnit extends InfantryUnit{

    /**
     * The default constructor for InfantryUnit.
     * @param name Name of the InfantryUnit.
     * @param health Health of the InfantryUnit, cannot be less than or equal to 0.
     * @param attack Attack-stat of the InfantryUnit, cannot be less than or equal to 0.
     * @param armor Armor-stat of the InfantryUnit, cannot be less than 0.
     */
    public SpearFighterUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        setUnitType(UnitType.SPEAR_FIGHTER_UNIT);
    }

    /**
     * The simplified constructor for InfantryUnit.
     * The InfantryUnit's attack and armor-stat are set to its default (15 and 10).
     * @param name Name of the InfantryUnit.
     * @param health Health of the InfantryUnit, cannot be less than or equal to 0.
     */
    public SpearFighterUnit(String name, int health){
        super(name, health);
        setUnitType(UnitType.SPEAR_FIGHTER_UNIT);
    }

    @Override
    public int getOpponentTypeBonus(UnitType unitType) {
        return switch (unitType){
            case CAVALRY_UNIT, COMMANDER_UNIT -> 5;
            default -> 0;
        };
    }
}
