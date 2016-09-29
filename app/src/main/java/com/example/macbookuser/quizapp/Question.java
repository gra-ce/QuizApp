package com.example.macbookuser.quizapp;

/**
 * Created by Macbookuser on 9/15/16.
 */
public class Question {
    private int questionId;
    private boolean isAnswerTrue;


    public Question(int questionId,boolean isAnswerTrue ){
        this.isAnswerTrue = isAnswerTrue;
        this.questionId = questionId;
    }

    /**
     * If answerGiven & isAnswerTrue match, it returns true
     * Otherwise, returns false
     *
     * @param answerGiven user's answer clicked
     * @return true if they got the question right
     * @return false if they got the question wrong
     */
    public boolean checkAnswer(boolean answerGiven){
        if(answerGiven == isAnswerTrue) {
            return true;
        }
        else{
                return false;
            }
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        isAnswerTrue = answerTrue;
    }



}
