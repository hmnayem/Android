package com.example.guru.lovemeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    LoveCalculations love;
    EditText nameX, nameY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameX = (EditText) findViewById(R.id.yourName);
        nameY = (EditText) findViewById(R.id.yourPartnersName);
    }

    public void getResult(View view) {
        display();
    }

    private void display() {

        String name1 = nameX.getText().toString();
        String name2 = nameY.getText().toString();

        love = new LoveCalculations(name1, name2);

        double result = love.calc();
        String finalresult = String.format("%.1f", result);
        TextView res = (TextView) findViewById(R.id.results);

        res.setText(finalresult + "%");

    }

}
