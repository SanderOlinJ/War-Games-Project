package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CavalryUnitTest {

    @Test
    @DisplayName("Does getAttackBonus() return correct values depending on number of calls to method")
    public void doesGetAttackBonusReturnCorrectValuesDependingOnNumberOfCalls(){
        CavalryUnit lightCavalry = new CavalryUnit("Light Cavalry", 100);
        assertEquals(6, lightCavalry.getAttackBonus());
        assertEquals(2, lightCavalry.getAttackBonus());
        assertEquals(2, lightCavalry.getAttackBonus());
    }
}