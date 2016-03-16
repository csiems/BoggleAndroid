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
}
