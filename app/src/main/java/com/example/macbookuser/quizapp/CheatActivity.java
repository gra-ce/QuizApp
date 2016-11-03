package com.example.macbookuser.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private boolean hasCheated;
    public static final String EXTRA_CHEATED = "They cheated";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        TextView questionText = (TextView) findViewById(R.id.cheat_question_text);
        final TextView questionAnswerText = (TextView) findViewById(R.id.cheat_answer);
        Button cheatButtonCheck = (Button) findViewById(R.id.button_are_you_sure);

        //gathering up the Intent that brought us here
        Intent i = getIntent();
        //extracting the extra that was put there
        int questionId = i.getIntExtra(com.example.macbookuser.quizapp.MainActivity.EXTRA_CURRENT_QUESTION, -500);
        final boolean answer = i.getBooleanExtra(com.example.macbookuser.quizapp.MainActivity.EXTRA_CURRENT_ANSWER, false);
        //using that data that was passed to the new activity
        questionText.setText(questionId);
        hasCheated = false;

        cheatButtonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionAnswerText.setText("" + answer);
                hasCheated = true;
                //just using this to box up the info to send back so
                //don't need any addresses in the parameters
                Intent i = new Intent();
                i.putExtra(EXTRA_CHEATED,hasCheated);
                setResult(RESULT_OK,i);



            }
        });


    }


}