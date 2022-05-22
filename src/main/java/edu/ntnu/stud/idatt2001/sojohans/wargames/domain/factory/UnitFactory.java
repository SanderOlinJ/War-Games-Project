package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.FactoryException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for producing Units using the Factory design pattern.
 */
public class UnitFactory {

    /**
     * Method for producing a single Unit.
     * Produces the Unit using the Unit class' simplified constructor.
     * This means the Unit is set with its default attack and armor stats.
     * @param unitType Type of Unit to be produced.
     * @param name Name of the Unit to be produced.
     * @param health Health of the Unit to be produced.
     * @return Unit of the UnitType.
     * @throws FactoryException If the UnitType and name argument is null
     * or if the health argument is less than or equal to zero.
     */
    public static Unit getUnit(UnitType unitType, String name, int health){

        if (unitType == null){
            throw new FactoryException("UnitType cannot be null");
        }
        if (name == null){
            throw new FactoryException("Unit name cannot be null");
        }
        if (health <= 0){
            throw new FactoryException("Unit health cannot be less than or equal to 0!");
        }
        return switch (unitType) {
            case SPEAR_FIGHTER_UNIT -> new SpearFighterUnit(name, health);
            case SWORDSMAN_UNIT -> new SwordsmanUnit(name, health);
            case AXEMAN_UNIT -> new AxemanUnit(name, health);
            case RANGED_UNIT -> new RangedUnit(name, health);
            case CAVALRY_UNIT -> new CavalryUnit(name, health);
            case COMMANDER_UNIT -> new CommanderUnit(name, health);
        };
    }

    /**
     * Method for producing multiple Units.
     * Produces a List of Units using UnitFactory's getUnits()-method.
     * @param unitType Type of Units to be produced.
     * @param name Name of the Units to be produced.
     * @param health Health of the Units to be produced.
     * @param numberOfUnits Number of Units to be produced.
     * @return Unit of the UnitType.
     * @throws FactoryException If the UnitType and name argument is null
     * or if the health and number of units argument is less than or equal to zero.
     */
    public static List<Unit> getCertainAmountOfUnits(UnitType unitType, String name,
                                                     int health, int numberOfUnits){
        if (unitType == null){
            throw new FactoryException("UnitType cannot be null!");
        }
        if (name == null){
            throw new FactoryException("Unit name cannot be null!");
        }
        if (health <= 0){
            throw new FactoryException("Unit health cannot be less than or equal to 0!");
        }
        if (numberOfUnits <= 0){
            throw new FactoryException("Number of units cannot be less than or equal 0!");
        }
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < numberOfUnits; i++){
            units.add(getUnit(unitType, name, health));
        }
        return units;
    }
}
