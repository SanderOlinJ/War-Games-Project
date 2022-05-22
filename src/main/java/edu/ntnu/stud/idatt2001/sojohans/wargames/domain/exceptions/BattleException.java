package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

/**
 * Class for describing a BattleException.
 * This Exception extends from RunTimeException and is thrown during Battle's simulation()-method.
 */
public class BattleException extends RuntimeException{

    /**
     * Constructor for instantiating a BattleException.
     * @param errorMessage Error message that is saved for later retrieval by Throwable's getMessage()-method.
     */
    public BattleException(String errorMessage){
        super(errorMessage);
    }
}
