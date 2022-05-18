package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitAttackException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;

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
     * Armor can however be 0, as full offensive Unit might be developed later on.
     * @param name Name of the Unit.
     * @param health Health of the Unit.
     * @param attack Attack-points of the Unit.
     * @param armor Armor-points of the Unit.
     * @throws IllegalArgumentException If parameters are invalid.
     */
    public Unit(String name, int health, int attack, int armor) throws IllegalArgumentException{
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name of Unit cannot be null or empty!");
        }
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
     * Method for attacking another Unit.
     * @param opponentUnit Opponent getting attacked.
     * @param terrainType Which terrain the attack takes place in (affects damage and armor outcome).
     * @throws UnitAttackException if either the health of the opponent or attacking Unit,
     * is equal to or less than 0.
     */
    public void attack(Unit opponentUnit, TerrainType terrainType) throws UnitAttackException{
        if (terrainType == null){
            throw new UnitAttackException("Terrain type cannot be null!");
        }
        if (opponentUnit.health <= 0){
            throw new UnitAttackException(opponentUnit.name + " has 0 or less health left!");
        }
        if (this.health <= 0){
            throw new UnitAttackException(this.name + " has 0 or less health left!");
        }
        else {
            /*
            Method gives the opponent new health, depending on attacker's attack and attack bonus,
            as well as the opponent's armor and resist bonus
             */
            opponentUnit.health = opponentUnit.health - (this.attack + this.getAttackBonus(terrainType))
                    + (opponentUnit.armor + opponentUnit.getResistBonus(terrainType));
        }
    }

    /**
     * Abstract method for getting the Unit's attack bonus.
     * @param terrainType Terrain, each terrain affects certain bonuses per units.
     * @return Attack bonus of the Unit.
     */
    public abstract int getAttackBonus(TerrainType terrainType);

    /**
     * Abstract method for getting the Unit's resist bonus.
     * @param terrainType Terrain, each terrain affects certain bonuses per units.
     * @return Resist bonus of the Unit.
     */
    public abstract int getResistBonus(TerrainType terrainType);


    /**
     * Method for getting the name of the Unit.
     * @return Name of the Unit.
     */
    public String getName() {
        return name;
    }

    /**
     * Method for getting the health of the Unit.
     * @return Health of the Unit.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method for setting the health of the Unit.
     * @param health Health of the Unit.
     */
    public void setHealth(int health) {
        if (health <= 0){
            throw new IllegalArgumentException("Health cannot be less than or equal to 0");
        }
        this.health = health;
    }

    /**
     * Method for getting the attack-points of the Unit.
     * @return Health of the Unit.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Method for getting the armor-points of the Unit.
     * @return Armor-points of the Unit.
     */
    public int getArmor() {
        return armor;
    }


    /**
     * Method for getting Unit as a String.
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
     * Default equals-method for comparing Units.
     * @param o Other Unit for comparison.
     * @return True if equals, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return health == unit.health && attack == unit.attack && armor == unit.armor && Objects.equals(name, unit.name);
    }

    /**
     * Default hashCode method for comparing Units.
     * @return Hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, health, attack, armor);
    }
}
