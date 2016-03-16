package com.epicodus.bogglesolo;

import android.content.res.TypedArray;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Guest on 3/16/16.
 */
public class MainActivityUnitTest extends MainActivity {

    @Test
    public void thereAreTwoVowels_notTwoVowels_false() throws Exception {
        ArrayList<String> letters = new ArrayList<>();
        letters.add("B");
        letters.add("V");
        assertTrue(!thereAreTwoVowels(letters));
    }

    @Test
    public void thereAreTwoVowels_twoVowels_true() throws Exception {
        ArrayList<String> letters = new ArrayList<>();
        letters.add("A");
        letters.add("E");
        assertTrue(thereAreTwoVowels(letters));
    }

    @Test
    public void allLettersAreInList_false() throws Exception {
        ArrayList<String> availableLetters = new ArrayList<>();
        String input = "CAT";
        availableLetters.add("D");
        availableLetters.add("E");
        availableLetters.add("F");
        availableLetters.add("L");
        availableLetters.add("Q");
        assertTrue(!allLettersAreInList(availableLetters, input));
    }

    @Test
    public void allLettersAreInList_true() throws Exception {
        ArrayList<String> availableLetters = new ArrayList<>();
        String input = "FED";
        availableLetters.add("D");
        availableLetters.add("E");
        availableLetters.add("F");
        availableLetters.add("L");
        availableLetters.add("Q");
        assertTrue(allLettersAreInList(availableLetters, input));
    }

    @Test
    public void allLettersAreInList_lettersCannotBeReused_false() throws Exception {
        ArrayList<String> availableLetters = new ArrayList<>();
        String input = "FEED";
        availableLetters.add("D");
        availableLetters.add("E");
        availableLetters.add("F");
        availableLetters.add("L");
        availableLetters.add("Q");
        assertTrue(!allLettersAreInList(availableLetters, input));
    }

    @Test
    public void allLettersAreInList_lettersCannotBeReused_true() throws Exception {
        ArrayList<String> availableLetters = new ArrayList<>();
        String input = "EOUIU";
        availableLetters.add("E");
        availableLetters.add("O");
        availableLetters.add("U");
        availableLetters.add("I");
        availableLetters.add("U");
        availableLetters.add("R");
        availableLetters.add("A");
        availableLetters.add("D");
        assertTrue(allLettersAreInList(availableLetters, input));
    }


}
