package com.epicodus.bogglesolo;

import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.inputsGridView) GridView mGridView;
    @Bind(R.id.rollButton) Button rollButton;
    @Bind(R.id.submitButton) Button submitButton;
    @Bind(R.id.userInputTextView) TextView userInputTextView;
    @Bind(R.id.inputsListView) ListView mListView;
    @Bind(R.id.countdownTextView) TextView countdownTextView;
    @Bind(R.id.wordCount) TextView wordCount;
    private TypedArray mDice;
    private ArrayList<String> mSelectedLetters;
    private ArrayList<String> mUserInputs = new ArrayList<>();
    private ArrayAdapter gridViewAdapter;
    private ArrayAdapter listViewAdapter;
    private int wordCountNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDice = getResources().obtainTypedArray(R.array.dice);
        submitButton.setEnabled(false);
        wordCountNumber = 0;
        wordCount.setText(R.string.word_count + wordCountNumber);

        //TODO: replace gridview adaptor with temp for modification, repopulate field after every submit.

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            cleanUpFromLastRound();

            ArrayList<Integer> diceToRoll = getDiceToRoll();
            mSelectedLetters = rollDice(diceToRoll);
            gridViewAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mSelectedLetters);
            mGridView.setAdapter(gridViewAdapter);

            beginCountdown();
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String letter = new String (parent.getItemAtPosition(position).toString());
                    gridViewAdapter.remove(letter);
                    gridViewAdapter.insert("", position);
                    gridViewAdapter.notifyDataSetChanged();
                    userInputTextView.append(letter);

                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String userInput;

                            if (userInputTextView.getText().toString().equals("ROLL THE DICE TO START")) {
                                makeToast("Roll the dice before submitting words");
                            } else {
                                userInput = validateInput(userInputTextView.getText().toString());
                                if (userInput.length() < 3) {
                                    makeToast("Enter a word of at least three letters");
                                } else if (!allLettersAreInList(mSelectedLetters, userInput)) {
                                    makeToast("You must go to Boggle with the letters you have, not the letters you wish you had.");
                                } else if (mUserInputs.contains(userInput)) {
                                    makeToast("Word already added");
                                } else {
                                    addInputToListView(userInput);
                                }
                            }
                            userInputTextView.setText("");

                        }
                    });

                }
            });
            }
        });
    }

    public ArrayList<Integer> getDiceToRoll() {
        ArrayList<Integer> grabbedDice = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Integer dieIndex = (int) (Math.random() * (mDice.length() - 1));
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
            int resId = mDice.getResourceId(diceToRoll.get(i), -1);
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

    public boolean allLettersAreInList(ArrayList<String> availableLetters, String input) {
        //we make a shallow copy of the the ArrayList to avoid removing letters from the member variable
        ArrayList<String> tempLetters = new ArrayList<>(availableLetters);

        String[] splitInput = input.split("");
        boolean allMatch = true;
        for (String letter : splitInput) {
            if (!tempLetters.contains(letter) && !letter.equals("")) {
                allMatch = false;
            } else {
                tempLetters.remove(letter);
            }
        }
        return allMatch;
    }

    public String validateInput(String input) {
        return input.toUpperCase().replaceAll("(?i)[^A-Z]", "");
    }

    public void makeToast(String message) {
        Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -30);
        toast.show();
    }

    public void beginCountdown() {
        rollButton.setVisibility(View.GONE);
        rollButton.setEnabled(false);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownTextView.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdownTextView.setText("Time's Up!");
                submitButton.setEnabled(false);
                userInputTextView.setText("");
                wordCountNumber = 0;
                rollButton.setVisibility(View.VISIBLE);
                rollButton.setEnabled(true);
            }
        }.start();
    }

    public void cleanUpFromLastRound() {
        mUserInputs.clear();
        wordCountNumber = 0;
        wordCount.setText(R.string.word_count + wordCountNumber);
    }

    public void addInputToListView(String input) {
        mUserInputs.add(0, input);
        listViewAdapter.notifyDataSetChanged();
        wordCountNumber++;
        wordCount.setText(R.string.word_count + wordCountNumber);
    }
}
