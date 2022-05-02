package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangedUnitTest {

    @Test
    @DisplayName("Does getResistBonus() return correct values depending on number of calls to method")
    public void doesGetResistBonusReturnCorrectValuesDependingOnNumberOfCalls(){
        RangedUnit archer = new RangedUnit("Archer",100);
        assertEquals(6,archer.getResistBonus());
        assertEquals(4,archer.getResistBonus());
        assertEquals(2,archer.getResistBonus());
        assertEquals(2,archer.getResistBonus());
    }
}