package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpearFighterUnitTest {

    @Test
    public void doesInstantiatingASpearFighterSetUnitType(){
        InfantryUnit infantryUnit = new SpearFighterUnit("Spear fighter",100);

        assertEquals(UnitType.SPEAR_FIGHTER_UNIT, infantryUnit.getUnitType());
    }

}