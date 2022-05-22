package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.factory.UnitType;

/**
 * Interface class OpponentTypeImpactsBonus, used for Units who
 * have advantages or disadvantages against certain UnitTypes, while attacking.
 */
public interface OpponentTypeImpactsBonus {

    /**
     * Method retrieves the OpponentTypeBonus a Unit receives against a certain UnitType,
     * while attacking.
     * @param unitType UnitType, determines the bonus outcome.
     * @return OpponentType bonus of the Unit.
     */
    int getOpponentTypeBonus(UnitType unitType);
}
