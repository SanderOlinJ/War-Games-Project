package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

/**
 * Class for describing a TerrainException.
 * This Exception extends from RunTimeException and is thrown if a Terrain is not given
 * when it is required.
 */
public class TerrainException extends RuntimeException{

    /**
     * Constructor for instantiating a TerrainException.
     * @param errorMessage Error message that is saved for later retrieval by Throwable's getMessage()-method.
     */
    public TerrainException(String errorMessage){
        super(errorMessage);
    }
}
