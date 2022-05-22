package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

/**
 * Class for describing a FactoryException.
 * This Exception extends from RunTimeException and is thrown if Factory is unable to produce a Unit,
 * if invalid parameters were given.
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
