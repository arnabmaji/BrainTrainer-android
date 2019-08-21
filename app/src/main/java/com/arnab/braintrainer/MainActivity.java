package com.arnab.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.icu.util.ICUUncheckedIOException;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    TextView questionTextView;
    TextView timerTextView;
    TextView scoreTextView;
    TextView feedbackTextView;
    Button[] options;
    Button playAgainButton;
    CountDownTimer countDownTimer;
    Random random;
    int numLimit;
    int totalOptions;
    int currentCorrectOption;
    int timeLimit;
    int score;
    int questionsAttempted;
    String CORRECT_STRING;
    String INCORRECT_STRING;
    ConstraintLayout mainGameConstraintLayout;

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        mainGameConstraintLayout.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        playAgain(playAgainButton);
    }

    public void playAgain(View view){
        playAgainButton.setVisibility(View.INVISIBLE);
        countDownTimer.start();
        feedbackTextView.setText("Not played yet");
        questionsAttempted = 0;
        score = 0;
        updateQuestion();
        scoreTextView.setText(score + "/" + questionsAttempted);
        for(Button opt : options){
            opt.setClickable(true);
        }
    }

    public void optionClicked(View view) {
        String clickedOption = view.getTag().toString();
        checkAnswer(Integer.parseInt(clickedOption));
    }

    public void updateQuestion() {
        questionsAttempted++;
        int randomNumber1 = random.nextInt(numLimit);
        int randomNumber2 = random.nextInt(numLimit);

        String questionText = randomNumber1 + "+" + randomNumber2;
        questionTextView.setText(questionText);
        int answer = randomNumber1 + randomNumber2;
        int randomCorrectOption = random.nextInt(totalOptions);
        currentCorrectOption = randomCorrectOption;

        for (int i = 0; i < totalOptions; i++) {
            if (i == randomCorrectOption) {
                String answerString = "" + answer;
                options[i].setText(answerString);
            } else {
                int randomNumer = random.nextInt(2 * numLimit);

                while (randomNumer == answer) {
                    randomNumer = random.nextInt(2 * numLimit);
                }
                String string = "" + randomNumer;
                options[i].setText(string);
            }
        }
    }

    public void checkAnswer(int clickedOption) {

        if (currentCorrectOption == clickedOption) {
            score++;
            feedbackTextView.setText(CORRECT_STRING);
        } else {
            feedbackTextView.setText(INCORRECT_STRING);
        }
        scoreTextView.setText(score + "/" + questionsAttempted);
        updateQuestion();
    }

    public void setTimerTextView(int l) {
        String string = "" + l + "s";
        if (l < 10) {
            string = "0" + l + "s";
        }
        timerTextView.setText(string);
    }

    public void gameFinishPrompt() {
        feedbackTextView.setText("Game Finished");
        setTimerTextView(0);
        for (Button opt : options) {
            opt.setText("");
            opt.setClickable(false);
        }
        questionTextView.setText("");
        playAgainButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        feedbackTextView = findViewById(R.id.answerFeedBackTextView);
        feedbackTextView.setText("not answered");
        options = new Button[4];
        options[0] = findViewById(R.id.button0);
        options[1] = findViewById(R.id.button1);
        options[2] = findViewById(R.id.button2);
        options[3] = findViewById(R.id.button3);
        random = new Random();
        numLimit = 20;
        totalOptions = 4;
        score = 0;
        CORRECT_STRING = "Correct";
        INCORRECT_STRING = "Wrong!";
        timeLimit = 30;
        mainGameConstraintLayout = findViewById(R.id.mainGameConstraintLayout);
        playAgainButton = findViewById(R.id.playAgainButton);
        countDownTimer = new CountDownTimer(timeLimit * 1000 + 150L, 1000) {
            @Override
            public void onTick(long l) {
                setTimerTextView((int) (l / 1000));
            }

            @Override
            public void onFinish() {
                gameFinishPrompt();

            }
        };
        mainGameConstraintLayout.setVisibility(View.INVISIBLE);
    }
}
