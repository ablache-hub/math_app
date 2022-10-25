package com.alex.mathapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final long START_TIMER = 60000;
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
                pauseTimer();

                if (TextUtils.isEmpty(answer.getText())) {
                    Toast.makeText(Game.this, "Entrez une valeur", Toast.LENGTH_SHORT).show();

                } else {
                    userAnswer = Integer.parseInt(answer.getText().toString());
                    ok.setEnabled(false);

                    if (userAnswer == realAnswer && userAnswer != 0) {
                        userScore += 10;
                        score.setText(String.valueOf(userScore));
                        question.setText(R.string.bravo);

                    } else {
                        userLife -= 1;
                        life.setText(String.valueOf(userLife));
                        question.setText(R.string.sorry);
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
                answer.setText("");
                gameContinue();

                if(userLife == 0) {
                    Intent intent = new Intent(getApplicationContext(), Result.class);
                    intent.putExtra("score", userScore);

                    startActivity(intent);
                    finish();
                } else {
                    gameContinue();
                }
            }
        });
    }
    public void gameContinue() {
        ok.setEnabled(true);
        Intent intent = getIntent();
        String operationLabel = intent.getStringExtra("operationLabel");

        number1 = random.nextInt(10);
        number2 = random.nextInt(10);

        question.setText(makeOperation(operationLabel));
        startTimer();
    };

    public String makeOperation (String type) {
        String result = null;

        switch (type) {
            case "addition":
                realAnswer = number1 + number2;
                result = number1 + " + " + number2;
                break;
            case "multiplication":
                realAnswer = number1 * number2;
                result = number1 + " * " + number2;
                break;
            case "soustraction":
                realAnswer = number1 - number2;
                result = number1 + " - " + number2;
                break;
        }
        return result;
    }

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
                question.setText(R.string.timeOut);
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