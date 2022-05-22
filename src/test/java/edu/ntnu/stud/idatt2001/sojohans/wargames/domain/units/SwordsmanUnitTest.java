package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwordsmanUnitTest {

    @Test
    @DisplayName("Does SwordsmanUnit get increased damage bonus when attacking another SwordsmanUnit")
    public void doesSwordsmanUnitGetIncreasedDamageBonusWhenAttackingAnotherSwordsmanUnit(){
        InfantryUnit swordsman = new SwordsmanUnit("Swordsman", 100);

        assertEquals(1, swordsman.getOpponentTypeBonus(UnitType.SWORDSMAN_UNIT));
    }
}