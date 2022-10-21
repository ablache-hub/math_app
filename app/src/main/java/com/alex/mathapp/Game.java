package com.alex.mathapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    TextView score, life, time, question;
    EditText answer;
    Button ok, next;
    Random random = new Random();
    int number1, number2, userAnswer;
    int realAnswer;
    int userScore = 0;
    int userLife = 3;
    Boolean timeRunning;

    CountDownTimer timer;
    private static final long START_TIMER = 5000;
    long timeLeft = START_TIMER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.tvScore);
        life = findViewById(R.id.tvLife);
        time = findViewById(R.id.tvTime);
        question = findViewById(R.id.tvQuestion);
        answer = findViewById(R.id.etResponse);
        ok = findViewById(R.id.btnOk);
        next = findViewById(R.id.btnNext);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAnswer = Integer.parseInt(answer.getText().toString());

                pauseTimer();

                if(userAnswer == realAnswer) {
                    userScore += 10;
                    score.setText(String.valueOf(userScore));
                    question.setText("Bravo");

                } else {
                    userLife -= 1;
                    life.setText(String.valueOf(userLife));
                    question.setText("Désolé, mauvaise réponse");
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setText("");
                gameContinue();
                resetTimer();
            }
        });
    }
    public void gameContinue() {
        number1 = random.nextInt(10);
        number2 = random.nextInt(10);
        realAnswer = number1 + number2;

        String result = number1 + " + " + number2;

        question.setText(result);
        startTimer();
    };

    public void startTimer() {
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateText();
            }

            @Override
            public void onFinish() {
                pauseTimer();
                resetTimer();
                updateText();
                timeRunning = false;
                userLife -= 1;
                life.setText(String.valueOf(userLife));
                question.setText("Temps écoulé");
            }
        }.start();

        timeRunning = true;
    }

    private void updateText() {
        int second = (int) ((timeLeft / 1000) % 60);
        String timeLeft = String.format(Locale.getDefault(), "%02d", second);
        time.setText(timeLeft);
    }

    private void resetTimer() {
        timeLeft = START_TIMER;
        updateText();
    }

    private void pauseTimer() {
        timer.cancel();
        timeRunning = false;
    }
}