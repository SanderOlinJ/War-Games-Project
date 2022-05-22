package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.TerrainException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitAttackException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitTypeException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;

import java.util.Objects;

/**
 * Class for describing an abstract Unit.
 */
public abstract class Unit {
    private UnitType unitType;
    private final String name;
    private int health;
    private final int attack;
    private final int armor;


    /**
     * <p>
     *     The constructor method for instantiating a Unit object.
     * </p>
     * <p>
     *     Neither Health nor Attack can be zero or lower.
     * </p>
     * <p>
     *     Armor can however be zero, as full offensive Unit might be developed later on.
     * </p>
     * @param name Name of the Unit.
     * @param health Health of the Unit.
     * @param attack Attack-points of the Unit.
     * @param armor Armor-points of the Unit.
     * @throws IllegalArgumentException If the name argument is null or empty, if the health and attack
     * arguments is less than or equal to zero, or if the armor argument is less than zero.
     */
    public Unit(String name, int health, int attack, int armor)
    throws IllegalArgumentException{
        if (name == null || name.isBlank()){
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
     * Method for attacking another Unit and lowering said Unit's health.
     * @param opponentUnit Opponent getting attacked.
     * @param terrainType Which terrain the attack takes place in (may affect damage and armor bonus depending
     *                    on the UnitType).
     * @throws TerrainException If the TerrainType argument is null.
     * @throws UnitAttackException If the health argument of the opponent or the attacking Unit
     * is less than or equal to zero.
     */
    public void attack(Unit opponentUnit, TerrainType terrainType)
    throws TerrainException, UnitAttackException{
        if (terrainType == null){
            throw new TerrainException("Terrain type cannot be null!");
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
            int healthBeforeAttack = opponentUnit.getHealth();
            opponentUnit.health = opponentUnit.health -
                    (this.attack + this.getAttackBonus(terrainType, opponentUnit.getUnitType()))
                    + (opponentUnit.armor + opponentUnit.getResistBonus(terrainType));

            if (opponentUnit.health > healthBeforeAttack){
                /*
                This if-statement is not true with the current balancing of all unit damage and armor,
                but this may change further down the road when for example a Unit is invincible to "Magic Damage".
                 */
                opponentUnit.health = healthBeforeAttack;
            }
        }
    }

    /**
     * Abstract method for getting the Unit's attack bonus.
     * @param terrainType Terrain, each terrain affects certain bonuses per units.
     * @param unitType Opponents UnitType, some Units are strong against certain units.
     * @return Attack bonus of the Unit.
     */
    public abstract int getAttackBonus(TerrainType terrainType, UnitType unitType);

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
     * @throws IllegalArgumentException If the health argument is less than or equals to zero.
     */
    public void setHealth(int health)
    throws IllegalArgumentException{
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
     * Method for getting the UnitType of the Unit.
     * @return UnitType of the Unit.
     */
    public UnitType getUnitType() {
        return unitType;
    }

    /**
     * Method for setting the UnitType of the Unit.
     * @param unitType UnitType of the Unit.
     * @throws UnitTypeException If the UnitType argument is null.
     */
    public void setUnitType(UnitType unitType)
    throws UnitTypeException{
        if (unitType == null){
            throw new UnitTypeException("UnitType cannot be null!");
        }
        this.unitType = unitType;
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
