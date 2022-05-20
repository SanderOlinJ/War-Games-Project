package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTypeTest {

    @Test
    void doesValueOfAStringReturnCorrectUnitType(){

        UnitType unitType = UnitType.valueOf("SPEAR_FIGHTER_UNIT");
        assertEquals(UnitType.SPEAR_FIGHTER_UNIT, unitType);
    }
}