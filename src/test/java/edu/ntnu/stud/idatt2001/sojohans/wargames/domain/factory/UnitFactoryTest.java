package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.FactoryException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitFactoryTest {

    @Test
    void doesGetUnitReturnCorrectUnitType(){

        Unit unit = UnitFactory.getUnit(UnitType.COMMANDER_UNIT,"Name");
        assertEquals("CommanderUnit",unit.getClass().getSimpleName());
    }

    @Test
    void doesGetUnitThrowExceptionIfUnitTypeIsNull(){

        assertThrows(FactoryException.class, () -> UnitFactory.getUnit(null, "Null"));
    }

    @Test
    void doesGetUnitThrowExceptionIfUnitNameIsNull(){

        assertThrows(FactoryException.class, () -> UnitFactory.getUnit(UnitType.SPEAR_FIGHTER_UNIT, null));
    }
    @Test
    void getNumberOfUnitsReturnCorrectAmountOfSameUnit(){

        List<Unit> units = UnitFactory.getCertainAmountUnits(UnitType.SPEAR_FIGHTER_UNIT,
                "Swordsman", 100);
        assertEquals(100, units.size());
    }

}