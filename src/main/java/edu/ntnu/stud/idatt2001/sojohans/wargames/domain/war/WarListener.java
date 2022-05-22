package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.war;

/**
 * Interface class for WarListeners.
 * Used for the Observer design pattern.
 */
public interface WarListener {

    /**
     * Method for updating all WarListeners.
     */
    void update();
}
