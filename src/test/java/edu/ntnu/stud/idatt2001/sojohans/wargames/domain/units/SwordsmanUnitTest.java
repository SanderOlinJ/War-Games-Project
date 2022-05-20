package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwordsmanUnitTest {

    @Test
    public void doesSwordsmanGetBonusDamageAgainstAxeman(){
        InfantryUnit swordsman = new SwordsmanUnit("Swordsman",100);

        assertEquals(1, swordsman.getOpponentTypeBonus(UnitType.AXEMAN_UNIT));
    }

    @Test
    public void doesSwordsmanGetNoBonusAgainstItself(){
        InfantryUnit swordsman = new SwordsmanUnit("Swordsman", 100);

        assertEquals(0, swordsman.getOpponentTypeBonus(UnitType.SWORDSMAN_UNIT));
    }
}