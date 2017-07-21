package com.game.string.stringgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestionCount;
    private TextView mScoreCount;
    private TextView mQuestionNumber;
    private TextView mQuestion;

    private EditText mAnswer;
    private Button mSubmitBtn;

    private String [] mAllQuestions;
    private LinkedList<Questions> userQuestions;
    private GameQuestions gameQuestions;

    private int currentQuestionNumber;
    private int currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionCount = (TextView) findViewById(R.id.question_count);
        mScoreCount = (TextView) findViewById(R.id.score_count);
        mQuestionNumber = (TextView) findViewById(R.id.question_number);
        mQuestion = (TextView) findViewById(R.id.question_name);

        mAnswer = (EditText) findViewById(R.id.user_input);
        mSubmitBtn = (Button) findViewById(R.id.submit_button);

        mSubmitBtn.setOnClickListener(this);

        mAllQuestions = getResources().getStringArray(R.array.words);
        gameQuestions = new GameQuestions(mAllQuestions);

        userQuestions = gameQuestions.getList();

        currentQuestionNumber = 1;
        currentScore = 0;

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }




    private boolean isMatch(Questions question, String answer) {
        if (question.answer.equalsIgnoreCase(answer)) {
            return true;
        }

        return false;
    }

    private void updateUI() {
        if (currentQuestionNumber <= userQuestions.size()) {
            mQuestionCount.setText(currentQuestionNumber + "/10");
            mScoreCount.setText(currentScore + "");
            mQuestionNumber.setText("Q-" + currentQuestionNumber);
            mQuestion.setText(userQuestions.get(currentQuestionNumber - 1).question);

            mAnswer.setText("");
        }
        else {
            mScoreCount.setText(currentScore + "");
            Toast.makeText(this, "Game Over, Score : " + currentScore, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        String answer = mAnswer.getText().toString().trim();
        Questions question = userQuestions.get(currentQuestionNumber-1);

        if (isMatch(question, answer)) {
            Toast.makeText(this, "You Have Entered Correct Answer", Toast.LENGTH_SHORT).show();
            currentScore += 5;
        } else {
            Toast.makeText(this, "You Have Entered Wrong Answer", Toast.LENGTH_SHORT).show();
        }


        currentQuestionNumber++;

        updateUI();
    }
}

