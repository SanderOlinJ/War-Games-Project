package edu.ntnu.stud.idatt2001.sojohans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {

    @Test
    void doesShortenAndReplaceNonAlphaNumericCharactersInStringReplaceAllNonAlphaNumericCharacters(){
        String str = "  ?  '_ ,.String123ÆØÅ?!'";
        String str2 = "String123";
        assertEquals(str2, Utilities.shortenAndReplaceNonAlphaNumericCharactersInString(str));
    }

}