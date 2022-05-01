package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.WarGamesException;

import java.util.Objects;

/**
 * Class for describing a Unit.
 */
public abstract class Unit {

    private final String name;
    private int health;
    private final int attack;
    private final int armor;

    /**
     * The constructor method for a Unit object.
     * Neither Health nor Attack can be 0 or lower.
     * Armor can however be 0, as full offensive unit might be developed later on.
     * @param name Name of the unit.
     * @param health Health of the unit.
     * @param attack Attack-points of the unit.
     * @param armor Armor-points of the unit.
     * @throws IllegalArgumentException if parameters are invalid.
     */
    public Unit(String name, int health, int attack, int armor) throws IllegalArgumentException{
        if (health <= 0){
            throw new IllegalArgumentException("Health cannot be less than or equal to 0!");
        }
        if (attack <= 0){
            throw new IllegalArgumentException("Attack cannot be less than or equal to 0!");
        }
        if (armor < 0){
            throw new IllegalArgumentException("Armor cannot be less than 0!");
        }
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * Method for attacking another unit.
     * @param opponentUnit Opponent getting attacked.
     * @throws WarGamesException if either the health of the opponent or attacking unit,
     * is equal to or less than 0.
     */
    public void attack(Unit opponentUnit){
        if (opponentUnit.health <= 0){
            throw new WarGamesException(opponentUnit.name + " has 0 or less health left!");
        }
        if (this.health <= 0){
            throw new WarGamesException(this.name + " has 0 or less health left!");
        }
        else {
            /*
            Method gives the opponent new health, depending on attacker's attack and attack bonus,
            as well as the opponent's armor and resist bonus
             */
            opponentUnit.health = opponentUnit.health - (this.attack + this.getAttackBonus())
                    + (opponentUnit.armor + opponentUnit.getResistBonus());
        }
    }

    /**
     * Abstract method for getting the unit's attack bonus.
     * @return Attack bonus of the unit.
     */
    public abstract int getAttackBonus();

    /**
     * Abstract method for getting the unit's resist bonus.
     * @return Resist bonus of the unit.
     */
    public abstract int getResistBonus();


    /**
     * Method for getting the name of the unit.
     * @return Name of the unit.
     */
    public String getName() {
        return name;
    }

    /**
     * Method for getting the health of the unit.
     * @return Health of the unit.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method for setting the health of the unit.
     * @param health Health of the unit.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Method for getting the attack-points of the unit.
     * @return Health of the unit.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Method for getting the armor-points of the unit.
     * @return Armor-points of the unit.
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Method for getting Unit as a String
     * @return Unit as String.
     */
    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", attack=" + attack +
                ", armor=" + armor +
                '}';
    }

    /**
     * Default method for comparing units.
     * @param o other unit for comparison.
     * @return true if equals, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return health == unit.health && attack == unit.attack && armor == unit.armor && Objects.equals(name, unit.name);
    }

    /**
     * Default hashCode method for comparing units.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, health, attack, armor);
    }
}
