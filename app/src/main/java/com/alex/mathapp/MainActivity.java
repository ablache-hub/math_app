package com.alex.mathapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button addition;
    Button subtraction;
    Button multiplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addition = findViewById(R.id.btnAdd);
        subtraction = findViewById(R.id.btnSub);
        multiplication = findViewById(R.id.btnMult);

        addition.setOnClickListener(this);
        subtraction.setOnClickListener(this);
        multiplication.setOnClickListener(this);
    }

    // common onClick() for all buttons
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), Game.class);

        TextView clickedButtonId = findViewById(v.getId());
        String buttonContent = clickedButtonId.getText().toString();

            // send button label to Game activity
                intent.putExtra("operationLabel", buttonContent);
                startActivity(intent);
    }
}