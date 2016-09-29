package com.example.macbookuser.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button trueButton, falseButton, nextButton, previousButton, cheatButton;
    private TextView questionTextView;
    private Question[] questionBank = {
            new Question(R.string.question1_text, true),
            //do command minus to collapse and get the string form of it
            new Question(R.string.question2_text, true),
            new Question(R.string.question3_text, false),
            new Question(R.string.question4_text,false),
            new Question(R.string.question5_text,true),
            new Question(R.string.question6_text, true),
            new Question(R.string.question7_text, false),
            new Question(R.string.question8_text, false),
    };
    private int currentIndex;
    private boolean cheatingEnabled;
    private boolean hasCheated;

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CURRENT_QUESTION = "current question";
    public static final String EXTRA_CURRENT_ANSWER = "current answer";
    public static final int REQUEST_CHEATED = 0;

    private int scoreValue;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. Wire the buttons & the textview
        trueButton = (Button) findViewById(R.id.button_true);
        falseButton = (Button) findViewById(R.id.button_false);
        nextButton = (Button) findViewById(R.id.button_next);
        previousButton = (Button) findViewById(R.id.button_previous);
        cheatButton = (Button) findViewById(R.id.button_cheat);
        questionTextView = (TextView) findViewById(R.id.textView_question);
        score = (TextView) findViewById(R.id.textView_score);

        //2. Create a new Question object from
        //   the String resources
        //   Make a Question object & pass the string resource
        //   & answer in the constructor
        //Question q1 = new Question(R.string.question_cats,true);

        //3. Set the textView's text to the Question's text
        currentIndex = 0;
        questionTextView.setText(questionBank[currentIndex].getQuestionId());


        //4. Make a View.OnClickListener for each button
        //using the anonymous inner class way of doing things
        //Inside each button, call the question's checkAnswer method
        //and make an appropriate toast.
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                disableButtons();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                disableButtons();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
                enableButtons();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                previousQuestion();
                enableButtons();
            }
                                          });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use Intents to go from one Activity to another
                Intent openCheatActivity =
                        new Intent(MainActivity.this, com.example.macbookuser.quizapp.CheatActivity.class);
                //load up the intent with extra information to take
                //to the new activity
                //it follows the key:value pair format where
                //you have a key to identify the extra & a value that is
                //stored with it
                openCheatActivity.putExtra(EXTRA_CURRENT_QUESTION,
                        questionBank[currentIndex].getQuestionId());
                openCheatActivity.putExtra(EXTRA_CURRENT_ANSWER,
                        questionBank[currentIndex].isAnswerTrue());
                startActivityForResult(openCheatActivity, REQUEST_CHEATED);

            }
        });

        hasCheated = false;

        cheatingEnabled = false;
        cheatButton.setVisibility(View.GONE);
    }

    private void previousQuestion() {
        if(currentIndex==0){
            currentIndex=questionBank.length-1;
            questionTextView.setText(questionBank[currentIndex].getQuestionId());
        }

        else {
            currentIndex = currentIndex-- % questionBank.length;
            questionTextView.setText(questionBank[currentIndex].getQuestionId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //result code says what happened. data is what gets passed back to us
        //TODO: do something with the data that gets passed back
        if(resultCode == RESULT_OK && requestCode == REQUEST_CHEATED){
            //TODO extract the information from the intent "data"
            //now do something with it
            //true if they cheated. have this in the cheat activity
            //but not in this one
            //use getExtra to extract information from the intent "data"
            hasCheated = data.getBooleanExtra(CheatActivity.EXTRA_CHEATED, false);
            scoreValue--;



        }
    }

    private void enableButtons() {
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
    }

    private void disableButtons() {
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
    }

    private void updateQuestion() {
        currentIndex =
                (currentIndex + 1) % questionBank.length;
        questionTextView.setText(questionBank[currentIndex]
                .getQuestionId());
    }

    private void checkAnswer(boolean userResponse) {
        if(questionBank[currentIndex].checkAnswer(userResponse)) {
            Toast.makeText(MainActivity.this,
                    R.string.toast_correct,
                    Toast.LENGTH_SHORT).show();
            scoreValue++;
            score.setText(scoreValue);
        } else {
            Toast.makeText(MainActivity.this,
                    R.string.toast_false,
                    Toast.LENGTH_SHORT).show();
            if(scoreValue>0) {
                scoreValue--;
                score.setText(getString(R.string.scoreValue) + scoreValue);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_cheat:
                toggleCheating();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleCheating() {
        if(cheatingEnabled) {
            cheatingEnabled = false;
            cheatButton.setVisibility(View.GONE);
        } else
        {
            cheatingEnabled = true;
            cheatButton.setVisibility(View.VISIBLE);
        }
    }

    private void nextQuestion(){
        if(currentIndex==questionBank.length-1){
            currentIndex=0;
            questionTextView.setText(questionBank[currentIndex].getQuestionId());
        }
        else {
            currentIndex = (currentIndex + 1);
            questionTextView.setText(questionBank[currentIndex].getQuestionId());
        }
    }
}