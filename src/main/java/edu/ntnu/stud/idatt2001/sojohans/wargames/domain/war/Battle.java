package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.BattleException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitAttackException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrain.TerrainType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for describing a Battle.
 */
public class Battle{

    public Army getArmyOne() {
        return armyOne;
    }

    public Army getArmyTwo() {
        return armyTwo;
    }

    private final Army armyOne;
    private final Army armyTwo;

    // armyOneAttacks and armyTwoAttacks are variables used to check attack distributions.
    private int armyOneAttacks;
    private int armyTwoAttacks;

    private TerrainType terrainType;

    private final List<WarListener> warListeners = new ArrayList<>();


    /**
     * Constructor for Battle.
     * @param armyOne Army One fighting in the battle.
     * @param armyTwo Army Two fighting in the battle.
     * @throws IllegalArgumentException If either Army is null or have no Units.
     */
    public Battle(Army armyOne, Army armyTwo) throws IllegalArgumentException{
        if (armyOne == null){
            throw new IllegalArgumentException("Army 1 cannot be null");
        }
        if (armyTwo == null){
            throw new IllegalArgumentException("Army 2 cannot be null");
        }
        if (armyOne.equals(armyTwo)){
            throw new IllegalArgumentException("Army cannot fight itself");
        }
        if (!armyOne.hasUnits()){
            throw new IllegalArgumentException(armyOne.getName() + " needs to have Units to fight in a battle");
        }
        if (!armyTwo.hasUnits()){
            throw new IllegalArgumentException(armyTwo.getName() + " needs to have Units to fight in a battle");
        }

        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
    }

    /**
     * Method for simulating a battle between two Armies.
     * Method runs as long both Armies have Units
     * @return The victor of the Battle.
     */
    public Army simulate(){
        //As long as both Armies have Units left, the fight will continue. It will stop when one Army perishes.
        while (this.armyOne.hasUnits() && this.armyTwo.hasUnits()){
            /*
            The simulation is RNG-based (Random Number Generator), not turn-based.
            So which Army's turn it is to strike is purely decided on luck,
            hence the usage of a random number.
             */
            Random random = new Random();
            int randomNumber = random.nextInt(2);

            try {
                /*
                A random Unit from one Army get to attack a random Unit from the other Army.
                If the attacked Unit dies, it will be removed from their respective Army.
                 */
                Unit unitFromArmyOne = this.armyOne.getRandom();
                Unit unitFromArmyTwo = this.armyTwo.getRandom();

                if (randomNumber == 0){
                    unitFromArmyOne.attack(unitFromArmyTwo, terrainType);
                    armyOneAttacks++;
                    if (unitFromArmyTwo.getHealth() <= 0){
                        this.armyTwo.remove(unitFromArmyTwo);
                        try{
                            warListeners.forEach(WarListener::update);
                            Thread.sleep(30);
                        } catch (InterruptedException exception){
                            throw new BattleException(exception.getMessage());
                        }
                    }
                } else{
                    unitFromArmyTwo.attack(unitFromArmyOne, terrainType);
                    armyTwoAttacks++;
                    if (unitFromArmyOne.getHealth() <= 0){
                        this.armyOne.remove(unitFromArmyOne);
                        try {
                            warListeners.forEach(WarListener::update);
                            Thread.sleep(30);
                        } catch (InterruptedException exception){
                            throw new BattleException(exception.getMessage());
                        }
                    }
                }
            } catch (IllegalArgumentException | UnitAttackException exception){
                throw new BattleException(exception.getMessage());
            }
        }
        //Method return whichever Army has Units left.
        return getVictor();
    }

    /**
     * Method for getting Battle as a String.
     * @return Battle as String.
     */
    @Override
    public String toString() {
        return "\nBattle:" +
                "\nArmyOne = '" + armyOne + '\'' +
                "\nArmyTwo = '" + armyTwo + '\'' + "\n";
    }

    /**
     * Method for getting the victor of a Battle.
     * @return the Army that won.
     */
    public Army getVictor(){
        if (this.armyOne.hasUnits()){
            return this.armyOne;
        } else {
            return this.armyTwo;
        }
    }

    /**
     * Method for getting the loser of a Battle.
     * @return the Army that lost.
     */
    public Army getLoser(){
        if (!this.armyOne.hasUnits()){
            return this.armyOne;
        } else {
            return this.armyTwo;
        }
    }

    /**
     * Method for getting how many times armyOne has attacked armyTwo.
     * @return Number of attacks from armyOne.
     */
    public int getArmyOneAttacks() {
        return armyOneAttacks;
    }

    /**
     * Method for getting how many times armyTwo has attacked armyOne.
     * @return Number of attacks from armyTwo.
     */
    public int getArmyTwoAttacks() {
        return armyTwoAttacks;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public void addListener(WarListener warListener){
        if (warListener == null){
            throw new IllegalArgumentException("New WarListener cannot be null");
        }
        warListeners.add(warListener);
    }
}
