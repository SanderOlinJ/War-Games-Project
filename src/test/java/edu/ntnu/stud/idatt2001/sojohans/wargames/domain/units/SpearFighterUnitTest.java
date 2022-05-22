package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpearFighterUnitTest {

    @Test
    @DisplayName("Does SpearFighterUnit get increased damage bonus when attacking a CommanderUnit")
    public void doesSpearFighterUnitGetIncreasedDamageBonusWhenAttackingACommanderUnit(){
        InfantryUnit spearFighter = new SpearFighterUnit("Spear fighter", 100);

        assertEquals(5, spearFighter.getOpponentTypeBonus(UnitType.COMMANDER_UNIT));
    }

}