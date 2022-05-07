package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTypeTest {

    @Test
    void doesValueOfAStringReturnCorrectUnitType(){

        UnitType unitType = UnitType.valueOf("INFANTRY_UNIT");
        assertEquals(UnitType.INFANTRY_UNIT, unitType);
    }
}