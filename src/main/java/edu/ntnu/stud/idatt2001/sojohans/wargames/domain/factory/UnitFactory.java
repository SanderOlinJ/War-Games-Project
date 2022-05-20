package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.FactoryException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.*;

import java.util.ArrayList;
import java.util.List;

public class UnitFactory {

    public static Unit getUnit(UnitType unitType, String name){

        if (unitType == null){
            throw new FactoryException("UnitType cannot be null");
        }
        if (name == null){
            throw new FactoryException("Unit name cannot be null");
        }
        return switch (unitType) {
            case SPEAR_FIGHTER_UNIT -> new SpearFighterUnit(name, 100);
            case SWORDSMAN_UNIT -> new SwordsmanUnit(name, 100);
            case AXEMAN_UNIT -> new AxemanUnit(name, 100);
            case RANGED_UNIT -> new RangedUnit(name, 100);
            case CAVALRY_UNIT -> new CavalryUnit(name, 100);
            case COMMANDER_UNIT -> new CommanderUnit(name, 150);
        };
    }

    public static List<Unit> getCertainAmountUnits(UnitType unitType, String name,
                                                   int numberOfUnits){
        if (unitType == null){
            throw new FactoryException("UnitType cannot be null");
        }
        if (name == null){
            throw new FactoryException("Unit name cannot be null");
        }
        if (numberOfUnits <= 0){
            throw new FactoryException("Number of units cannot be less than or equals 0");
        }
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < numberOfUnits; i++){
            units.add(getUnit(unitType, name));
        }
        return units;
    }
}
