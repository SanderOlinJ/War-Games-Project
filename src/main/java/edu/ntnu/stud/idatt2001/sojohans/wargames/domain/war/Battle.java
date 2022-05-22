package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war;

import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.BattleException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.TerrainException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions.UnitAttackException;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.terrainAndOtherBonuses.TerrainType;
import edu.ntnu.stud.idatt2001.sojohans.wargames.domain.units.Unit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for describing a Battle, which takes place between two Armies
 * in a TerrainType.
 */
public class Battle{
    private final Army armyOne, armyTwo;

    /**
     * armyOneAttacks and armyTwoAttacks are variables used to check attack distributions.
     */
    private int armyOneAttacks, armyTwoAttacks;

    private final TerrainType terrainType;

    /**
     * List of WarListeners used to update the GUI each time
     * a Unit is removed (dies) from either of the Armies during a simulation.
     */
    private final List<WarListener> warListeners = new ArrayList<>();

    /**
     * stopRequested is a flag used for telling Battle to stop simulating.
     * This is so that a simulation does not keep on running even after the user
     * has closed the application.
     */
    private static boolean stopRequested = false;


    /**
     * The default constructor for instantiating a Battle between two Armies in a TerrainType.
     * @param armyOne Army One fighting in the battle.
     * @param armyTwo Army Two fighting in the battle.
     * @param terrainType TerrainType, Which terrain the Battle takes place in
     *                    (may affect damage and armor bonus for the Units in the
     *                    Armies depending on the UnitType).
     * @throws IllegalArgumentException If either Army argument is null, have no Units
     * or if they are both the same Army.
     * @throws TerrainException If the TerrainType is null.
     */
    public Battle(Army armyOne, Army armyTwo, TerrainType terrainType)
    throws IllegalArgumentException, TerrainException{
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
        if (terrainType == null){
            throw new TerrainException("TerrainType cannot be null!");
        }
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.terrainType = terrainType;
    }

    /**
     * Method for simulating a battle between two Armies in a Terrain.
     * Method runs as long as both Armies have Units left or and 'stopRequested' is false.
     * Simulate uses Random to determine which Army's turn it is to attack the other.
     * A random Unit from one of the Armies then attacks another random Unit from the other Army.
     * When a Unit's health is less than or equal to 0, it is removed from its respective Army.
     * Every WarListener in the list is updated and Thread.sleep() is set to 30 milliseconds
     * every time a Unit is removed from each Army.
     * @return The victor of the Battle.
     * @throws BattleException If exceptions were thrown from either Unit's getRandom(),
     * attack() or remove(), or if Thread.sleep() throws InterruptedException.
     */
    public Army simulate()
    throws BattleException{
        while (this.armyOne.hasUnits() && this.armyTwo.hasUnits() && !stopRequested){
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
                        /*
                        WarListeners in list are then updated.
                        They are then used in the Controllers (BattleSimulationController for now)
                        to update the GUI each time a Unit dies.
                         */
                        warListeners.forEach(WarListener::update);
                        try{
                            /*
                            Thread.sleep for 30 milliseconds, used for slowing down the Battle
                            Simulation in GUI,
                             */
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
                        warListeners.forEach(WarListener::update);
                        try {
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
        return getVictor();
    }

    /**
     * Method for getting the victor of a Battle.
     * @return an Army (Victor) if the other Army has no units, or null if
     * both Armies still have Units (Battle not done).
     */
    public Army getVictor(){
        if (this.armyOne.hasUnits() && !this.armyTwo.hasUnits()){
            return this.armyOne;
        } else if (!this.armyOne.hasUnits() && this.armyTwo.hasUnits()){
            return this.armyTwo;
        } else {
            return null;
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

    /**
     * Method for adding WarListeners to the Battle.
     * @param warListener Listeners used to update Battle results in other classes.
     * @throws IllegalArgumentException If the WarListener argument is null.
     */
    public void addListener(WarListener warListener)
    throws IllegalArgumentException{
        if (warListener == null){
            throw new IllegalArgumentException("New WarListener cannot be null");
        }
        warListeners.add(warListener);
    }

    /**
     * Method for shutting down simulate.
     * Sets the property 'stopRequested' to true, so that simulate will stop running.
     */
    public static void shutdownSimulate(){
        stopRequested = true;
    }

    /**
     * Method for enabling simulate to commence on method call.
     * Sets the property 'stopRequested' back to its native state, false.
     */
    public static void enableSimulate(){
        stopRequested = false;
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
}
