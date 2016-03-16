package com.epicodus.bogglesolo;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.lettersTextView) TextView lettersTextView;
    @Bind(R.id.rollButton) Button rollButton;
    @Bind(R.id.submitButton) Button submitButton;
    private TypedArray dice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dice = getResources().obtainTypedArray(R.array.dice);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> diceToRoll = getDiceToRoll();
                ArrayList<String> selectedLetters = rollDice(diceToRoll);
                String newLetterString = getLetterString(selectedLetters);
                lettersTextView.setText(newLetterString);
            }
        });
    }

    public ArrayList<Integer> getDiceToRoll() {
        ArrayList<Integer> grabbedDice = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Integer dieIndex = (int) (Math.random() * (dice.length() - 1));
            if (grabbedDice.contains(dieIndex)) {
                i--;
            } else {
                grabbedDice.add(dieIndex);
            }
        }
        return grabbedDice;
    }

    public ArrayList<String> rollDice(ArrayList<Integer> diceToRoll) {
        TypedArray specificDie;
        ArrayList<String> letters = new ArrayList<>();
        for (int i = 0; i < diceToRoll.size(); i++) {
            int resId = dice.getResourceId(diceToRoll.get(i), -1);
            if (resId < 0) {
                continue;
            }
            specificDie = getResources().obtainTypedArray(resId);
            int rolledSide = (int) (Math.random() * 5);
            letters.add(specificDie.getString(rolledSide));
            specificDie.recycle();
        }

        if (!thereAreTwoVowels(letters)) {
            return rollDice(diceToRoll);
        } else {
            return letters;
        }
    }

    public String getLetterString(ArrayList<String> selectedLetters) {
        String selectedLetterString = "";
        for (String s : selectedLetters) {
            selectedLetterString += s + " ";
        }
        selectedLetterString = selectedLetterString.substring(0, selectedLetterString.length()-1);
        return selectedLetterString;
    }

    public boolean thereAreTwoVowels(ArrayList<String> letters) {
        int vowelCount = 0;
        String vowels = "AEIOU";
        for (String letter : letters) {
            if (vowels.contains(letter)) {
                vowelCount++;
            }
        }
        return vowelCount >= 2;
    }
}
