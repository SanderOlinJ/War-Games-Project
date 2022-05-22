package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.InfantryUnit;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.SpearFighterUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTypeTest {

    @Test
    @DisplayName("Does value of a string return correct unit type")
    void doesValueOfAStringReturnCorrectUnitType(){
        UnitType unitType = UnitType.valueOf("SPEAR_FIGHTER_UNIT");
        assertEquals(UnitType.SPEAR_FIGHTER_UNIT, unitType);
    }

    @Test
    @DisplayName("Does instantiating a SpearFighterUnit set UnitType")
    public void doesInstantiatingASpearFighterSetUnitType(){
        InfantryUnit infantryUnit = new SpearFighterUnit("Spear fighter",100);

        assertEquals(UnitType.SPEAR_FIGHTER_UNIT, infantryUnit.getUnitType());
    }
}