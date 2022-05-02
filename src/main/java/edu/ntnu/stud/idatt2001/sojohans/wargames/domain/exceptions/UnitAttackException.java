package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

public class UnitAttackException extends RuntimeException{

    public UnitAttackException(String errorMessage){
        super(errorMessage);
    }
}
