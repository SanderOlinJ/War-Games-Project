package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

/**
 * <p>
 *     Class for describing a UnitTypeException.
 * </p>
 * <p>
 *     This Exception extends from RunTimeException and is thrown if a UnitType is not given
 *     when it is required.
 * </p>
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
