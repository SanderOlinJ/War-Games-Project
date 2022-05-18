package edu.ntnu.stud.idatt2001.sojohans.wargames.domain.exceptions;

public class BattleException extends RuntimeException{

    public BattleException(String errorMessage){
        super(errorMessage);
    }
}
