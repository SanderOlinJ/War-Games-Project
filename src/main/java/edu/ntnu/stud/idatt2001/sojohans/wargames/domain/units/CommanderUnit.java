package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;

/**
 * Class for describing a CommanderUnit.
 */
public class CommanderUnit extends CavalryUnit{

    /**
     * chargedAttack is a variable that tells if the unit has used its charge attack (the first attack).
     * It is specifically used in the unit's getAttackBonus method, where it determines how much
     * Attack Bonus it should get.
     */
    private boolean chargedAttack;

    /**
     * The default constructor for CommanderUnit.
     * @param name Name of the CommanderUnit.
     * @param health Health of the CommanderUnit, cannot be less than or equal to 0.
     * @param attack Attack-stat of the CommanderUnit, cannot be less than or equal to 0.
     * @param armor Armor-stat of the CommanderUnit, cannot be less than 0.
     */
    public CommanderUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        chargedAttack = false;
    }

    /**
     * The simplified constructor for CommanderUnit.
     * The CommanderUnit's attack and armor-stat are set to its default (25 and 15).
     * @param name Name of the CommanderUnit.
     * @param health Health of the CommanderUnit, cannot be less than or equal to 0.
     */
    public CommanderUnit(String name, int health){
        super(name, health, 25, 15);
        chargedAttack = false;
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
