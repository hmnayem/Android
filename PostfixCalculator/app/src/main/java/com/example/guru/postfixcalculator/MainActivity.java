package com.example.guru.postfixcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    TextView postfixView;
    TextView resultView;

    PostfixConverter converter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void converResult(View view) throws Exception {
        display();
    }

    public void reset(View view) {

        String string = inputText.getText().toString();

        if (!string.matches("")) {
            inputText.setText("");
            postfixView.setText("Ex: A B C D * + E F / +");
            resultView.setText("N/A");
        } else {
            inputText.requestFocus();
        }

    }

    private void display() throws Exception {

        inputText = (EditText) findViewById(R.id.inputBox);
        postfixView = (TextView) findViewById(R.id.postfix);
        resultView = (TextView) findViewById(R.id.result);

        String infix = inputText.getText().toString();

        if (!infix.matches("")) {

            converter = new PostfixConverter(infix);
            postfixView.setText(converter.getPostfix());
            resultView.setText(converter.getResult());
        }
        else {
            postfixView.setText("Empty Expression");
        }

    }
}
