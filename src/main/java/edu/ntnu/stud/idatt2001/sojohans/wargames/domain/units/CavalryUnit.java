package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

/**
 * Class for describing a CavalryUnit.
 */
public class CavalryUnit extends Unit{

    /**
     * chargedAttack is a variable that tells if the unit has used its charge attack (the first attack).
     * It is specifically used in the unit's getAttackBonus method, where it determines how much
     * Attack Bonus it should get.
     */
    private boolean chargedAttack;

    /**
     * The default constructor for CavalryUnit.
     * @param name Name of the CavalryUnit.
     * @param health Health of the CavalryUnit, cannot be less than or equal to 0.
     * @param attack Attack-stat of the CavalryUnit, cannot be less than or equal to 0.
     * @param armor Armor-stat of the CavalryUnit, cannot be less than 0.
     */
    public CavalryUnit(String name, int health, int attack,int armor){
        super(name, health, attack, armor);
        chargedAttack = false;
    }

    /**
     * The simplified constructor for CavalryUnit.
     * The CavalryUnit's attack and armor-stat are set to its default (20 and 12).
     * @param name Name of the CavalryUnit.
     * @param health Health of the CavalryUnit, cannot be less than or equal to 0.
     */
    public CavalryUnit(String name, int health){
        super(name, health, 20, 12);
        chargedAttack = false;
    }

    /**
     * Method for retrieving the CavalryUnit's attack bonus.
     * Varies depending on if the unit has used its charged attack.
     * @return the Attack bonus for CavalryUnit, 6 and 2.
     */
    @Override
    public int getAttackBonus() {
        if (!this.chargedAttack){
            chargedAttack = true;
            return 6;
        }else {
            return 2;
        }
    }
    /**
     * Method for retrieving the CavalryUnit's resist bonus.
     * @return Resist bonus of the CavalryUnit.
     */
    @Override
    public int getResistBonus() {
        return 1;
    }

    /**
     * Method for getting CavalryUnit as a String.
     * @return CavalryUnit as String.
     */
    @Override
    public String toString() {
        return "\nInfantry " + super.toString();
    }

}
