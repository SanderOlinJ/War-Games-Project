package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.FactoryException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitFactoryTest {

    @Test
    @DisplayName("Does getUnit return correct unit type")
    void doesGetUnitReturnCorrectUnitType(){

        Unit unit = UnitFactory.getUnit(UnitType.COMMANDER_UNIT,"Name",100);
        assertEquals(UnitType.COMMANDER_UNIT,unit.getUnitType());
    }

    @Test
    @DisplayName("Does getUnit throw exception if unit type is null")
    void doesGetUnitThrowExceptionIfUnitTypeIsNull(){

        assertThrows(FactoryException.class, () -> UnitFactory.getUnit(null, "Null", 100));
    }

    @Test
    @DisplayName("Does getUnit throw exception if unit name is null")
    void doesGetUnitThrowExceptionIfUnitNameIsNull(){

        assertThrows(FactoryException.class, () -> UnitFactory.getUnit(UnitType.SPEAR_FIGHTER_UNIT, null,
                100));
    }

    @Test
    @DisplayName("Does getUnit throw exception if health is invalid")
    void doesGetUnitThrowExceptionIfHealthIsInvalid(){
        assertThrows(FactoryException.class, () -> UnitFactory.getUnit(UnitType.SWORDSMAN_UNIT, "Swordsman",
                -3));
    }
    @Test
    @DisplayName("Does getCertainAmountOfUnits return correct amount of same unit type")
    void getNumberOfUnitsReturnCorrectAmountOfSameUnitType(){

        List<Unit> units = UnitFactory.getCertainAmountOfUnits(UnitType.SPEAR_FIGHTER_UNIT,
                "Swordsman", 100, 10);
        assertEquals(10, units.size());
    }

}