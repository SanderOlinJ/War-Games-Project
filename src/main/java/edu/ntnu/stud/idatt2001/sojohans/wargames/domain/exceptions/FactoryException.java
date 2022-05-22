package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

/**
 * <p>
 *     Class for describing a FactoryException.
 * </p>
 * <p>
 *     This Exception extends from RunTimeException and is thrown if Factory is unable to produce a Unit,
 *     if invalid parameters were given.
 * </p>
 */
public class FactoryException extends RuntimeException{

    /**
     * Constructor for instantiating a FactoryException.
     * @param errorMessage Error message that is saved for later retrieval by Throwable's getMessage()-method.
     */
    public FactoryException(String errorMessage){
        super(errorMessage);
    }
}
