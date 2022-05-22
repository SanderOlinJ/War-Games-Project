package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

/**
 * Class for describing a UnitTypeException.
 * This Exception extends from RunTimeException and is thrown if a UnitType is not given
 * when it is required.
 */
public class UnitTypeException extends RuntimeException {

    /**
     * Constructor for instantiating a UnitTypeException.
     * @param errorMessage Error message that is saved for later retrieval by Throwable's getMessage()-method.
     */
    public UnitTypeException(String errorMessage){
        super(errorMessage);
    }
}
