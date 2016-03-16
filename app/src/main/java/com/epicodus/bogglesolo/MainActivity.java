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
                ArrayList<String> selectedLetters = new ArrayList<>();
                TypedArray itemDef;

                for (int i = 0; i < dice.length(); i++) {
                    int resId = dice.getResourceId(i, -1);
                    if (resId < 0) {
                        continue;
                    }
                    itemDef = getResources().obtainTypedArray(resId);
                    int rolledSide = (int) (Math.random() * 5);

                    selectedLetters.add(itemDef.getString(rolledSide));
                }

                String newLetterString = getLetterString(selectedLetters);
                lettersTextView.setText(newLetterString);
            }

        });


    }

    public String getLetterString(ArrayList<String> selectedLetters) {
        String selectedLetterString = "";
        for (String s : selectedLetters) {
            selectedLetterString += s + " ";
        }
        selectedLetterString = selectedLetterString.substring(0, selectedLetterString.length()-1);
        return selectedLetterString;
    }
}
