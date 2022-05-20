package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;

public interface OpponentTypeImpactsBonus {

    int getOpponentTypeBonus(UnitType unitType);
}
