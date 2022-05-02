package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Class for describing an army.
 */
public class Army {

    private final String name;
    private final ArrayList<Unit> units;

    /**
     * Basic constructor for army class.
     * @param name Name of the army.
     */
    public Army(String name){
        this.name = name;
        this.units = new ArrayList<>();
    }

    /**
     * Constructor for army class, if its to be assigned with units at initialization.
     * @param name Name of the army.
     * @param newUnits ArrayList of units in the army, cannot be of size == 0 or null.
     * Sends the ArrayList to addAllUnits() which deep-copies all units.
     */
    public Army(String name, ArrayList<Unit> newUnits) {
        this.name = name;
        this.units = new ArrayList<>();
        addAllUnits(newUnits);
    }

    /**
     * Method for adding an ArrayList of units to an army.
     * @param newUnits ArrayList of units to be added.
     * @throws IllegalArgumentException If ArrayList is empty or null.
     * or if the units in the list either are null or have invalid health.
     */
    public void addAllUnits(ArrayList<Unit> newUnits) throws IllegalArgumentException{
        if (newUnits == null || newUnits.size() == 0){
            throw new IllegalArgumentException("Unit list is empty or null!");
        }
        /*
        Method sends each unit in the list to addUnit().
        addUnit() then sends it to deepCopyUnit for deep-copying the unit,
        before it's added to the army's ArrayList of units.
         */
        newUnits.forEach(this::addUnit);
    }

    /**
     * Method for adding a unit to an army.
     * @param unit Unit to be added.
     * @throws IllegalArgumentException If unit is null or the unit's health is less than
     * or equals to 0.
     */
    public void addUnit(Unit unit) throws IllegalArgumentException{
        if (unit == null){
            throw new IllegalArgumentException("Unit is null!");
        }
        else if (unit.getHealth() <= 0){
            throw new IllegalArgumentException("Unit health must be higher than 0!");
        }
        /*
        Method adds the unit to the army's ArrayList of units,
        after it's been deep-copied, using deepCopyUnit();
         */
        this.units.add(deepCopyUnit(unit));
    }

    /**
     * Method takes in a unit and deep-copies it.
     * Has to be a subclass of Unit.
     * @param newUnit Unit wished to be deep-copied.
     * @return Deep-copied Unit.
     * @throws IllegalArgumentException If the unit class is not recognised.
     */
    private Unit deepCopyUnit(Unit newUnit) throws IllegalArgumentException{
        return switch (newUnit.getClass().getSimpleName()) {
            case "InfantryUnit" -> new InfantryUnit(newUnit.getName(), newUnit.getHealth(),
                    newUnit.getAttack(), newUnit.getArmor());
            case "RangedUnit" -> new RangedUnit(newUnit.getName(), newUnit.getHealth(),
                    newUnit.getAttack(), newUnit.getArmor());
            case "CavalryUnit" -> new CavalryUnit(newUnit.getName(), newUnit.getHealth(),
                    newUnit.getAttack(), newUnit.getArmor());
            case "CommanderUnit" -> new CommanderUnit(newUnit.getName(), newUnit.getHealth(),
                    newUnit.getAttack(), newUnit.getArmor());
            default -> throw new IllegalArgumentException("Unit class not recognised!");
        };
        
    }

    /**
     * Method for getting name of the army.
     * @return Name of the army.
     */
    public String getName() {
        return name;
    }

    /**
     * Method removes unit from the army's ArrayList of units.
     * @param unit Unit desired to be removed.
     * @throws IllegalArgumentException If unit is not in the ArrayList.
     */
    public void remove(Unit unit) throws IllegalArgumentException{
        if (!this.units.remove(unit)){
            throw new IllegalArgumentException("Unit not found in ArrayList!");
        }
    }

    /**
     * Method checks if the Army has any units in its ArrayList of units.
     * @return True or false, depending on whether it has units or not.
     */
    public boolean hasUnits(){
        return this.units != null && this.units.size() != 0;
    }

    /**
     * Method shallow-copies and returns an ArrayList of units.
     * @return ArrayList of units in the army.
     */
    public ArrayList<Unit> getUnits() {
        return new ArrayList<>(this.units);
    }

    /**
     * Method for returning a random unit from the ArrayList of units.
     * @return A random unit.
     * @throws IllegalArgumentException If there are no units in the ArrayList of units.
     */
    public Unit getRandom() throws IllegalArgumentException{
        if (this.units == null || this.units.size() == 0){
            throw new IllegalArgumentException("There are no units in this army!");
        }
        Random random = new Random();
        int index = random.nextInt(this.units.size());
        return this.units.get(index);
    }

    /**
     * Method for getting army as a String.
     * @return Army as String
     */
    @Override
    public String toString() {
        return "\nArmy: " +
                "\nName='" + name + '\'' +
                "\nunits=" + units + '\'' + "\n";
    }

    /**
     * Default equals-method for comparing armies.
     * @param o Other army for comparison.
     * @return True if equals, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return Objects.equals(name, army.name) && Objects.equals(units, army.units);
    }

    /**
     * Default hashCode method for comparing armies.
     * @return Hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }
}
