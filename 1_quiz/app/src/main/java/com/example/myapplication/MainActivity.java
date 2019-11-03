package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.Question;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "QUIZACTIVITY";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEAT = "cheat";
    private static final int REQUEST_CODE_CHEAT = 0;

    private final boolean mIsDecr = true;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private int mAnsweredCount;
    private int mCorrect;
//    private boolean mIsCheater;

    // Should be an adapter??
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_ocean, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        mAnsweredCount = 0;

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
      //      mIsCheater = savedInstanceState.getBoolean(KEY_INDEX, false);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });


        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = Cheat.newIntent(MainActivity.this, isTrue);
               // startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });


        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIndex(!mIsDecr);
//                mIsCheater = false;
                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIndex(mIsDecr);
//                mIsCheater = false;
                updateQuestion();
            }
        });
    }


    private void updateIndex(boolean isDecr){
        if(isDecr){
            do {
                mCurrentIndex--;
                if(mCurrentIndex < 0){
                    mCurrentIndex += mQuestionBank.length;
                }
            }
            while (mQuestionBank[mCurrentIndex].isAnswered());
        }
        else{
            do{ mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length; }
            while (mQuestionBank[mCurrentIndex].isAnswered());
        }
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        if(mQuestionBank[mCurrentIndex].isAnswered()){
            Toast.makeText(this, R.string.already_answered, Toast.LENGTH_SHORT).show();
            return;
        }

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if(mQuestionBank[mCurrentIndex].isCheated()){
            messageResId = R.string.judgement_toast;
        }else{
            if(userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
                mCorrect++;
            }
            else{
                messageResId = R.string.incorrect_toast;
            }
        }


        mQuestionBank[mCurrentIndex].setAnswered(true);
        mAnsweredCount++;

        Toast t = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP, 0, 0);
        t.show();

        checkFinish();
    }


    public void checkFinish(){
        if(mAnsweredCount == mQuestionBank.length) {
            String result = mCorrect + " / " + mQuestionBank.length;
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            mAnsweredCount = 0;

            // restart quiz
            for(int i = 0; i < mQuestionBank.length; i++)
                mQuestionBank[i].resetQuestion();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            if(Cheat.wasAnswerShown(data)){
                mQuestionBank[mCurrentIndex].setCheated(true);
            }
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    // saving the index for when device is rotated(onDestroy is called and then onCreate)
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
}
