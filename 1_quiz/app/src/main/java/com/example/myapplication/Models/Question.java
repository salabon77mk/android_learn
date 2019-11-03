package com.example.myapplication.Models;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswered;
    private boolean mCheated;

    public boolean isCheated() { return mCheated; }

    public void setCheated(boolean cheated) { mCheated = cheated; }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswered() {
        return mAnswered;
    }

    public void setAnswered(boolean answered) {
        mAnswered = answered;
    }

    public Question(int textResId, boolean answerTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswered = false;
        mCheated = false;
    }

    public void resetQuestion(){
        mCheated = false;
        mAnswered = false;
    }
}
