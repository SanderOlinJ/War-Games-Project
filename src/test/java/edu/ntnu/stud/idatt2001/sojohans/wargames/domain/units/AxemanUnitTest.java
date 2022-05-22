package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AxemanUnitTest {

    @Test
    @DisplayName("Does AxemanUnit get increased damage bonus when attacking a RangedUnit")
    public void doesAxemanUnitGetIncreasedDamageBonusWhenAttackingARangedUnit(){
        InfantryUnit axemanUnit = new AxemanUnit("Axeman", 100);

        assertEquals(1, axemanUnit.getOpponentTypeBonus(UnitType.RANGED_UNIT));
    }
}