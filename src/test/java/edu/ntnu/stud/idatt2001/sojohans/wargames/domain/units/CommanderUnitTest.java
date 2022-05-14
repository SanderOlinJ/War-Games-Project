package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommanderUnitTest {

    @Test
    @DisplayName("Does getAttackBonus() for CommanderUnit return correct values depending on number of calls to method")
    public void doesGetAttackBonusForCommanderUnitReturnCorrectValuesDependingOnNumberOfCalls(){
        CommanderUnit commander = new CommanderUnit("Commander", 100);
        assertEquals(6, commander.getAttackBonus(TerrainType.HILL));
        assertEquals(2, commander.getAttackBonus(TerrainType.HILL));
        assertEquals(2, commander.getAttackBonus(TerrainType.HILL));
    }

}