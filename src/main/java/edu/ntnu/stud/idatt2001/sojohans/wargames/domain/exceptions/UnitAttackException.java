package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

/**
 * Class for describing a UnitAttackException.
 * This Exception extends from RunTimeException and is thrown during Unit's attack()-method.
 */
public class UnitAttackException extends RuntimeException{

    /**
     * Constructor for instantiating a UnitAttackException.
     * @param errorMessage Error message that is saved for later retrieval by Throwable's getMessage()-method.
     */
    public UnitAttackException(String errorMessage){
        super(errorMessage);
    }
}
