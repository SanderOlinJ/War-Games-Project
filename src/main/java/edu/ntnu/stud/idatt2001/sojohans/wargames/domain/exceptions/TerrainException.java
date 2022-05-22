package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

/**
 * <p>
 *     Class for describing a TerrainException.
 * </p>
 * <p>
 *     This Exception extends from RunTimeException and is thrown if a Terrain is not given
 *     when it is required.
 * </p>
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
