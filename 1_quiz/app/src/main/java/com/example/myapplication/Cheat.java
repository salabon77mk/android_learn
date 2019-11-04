package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Cheat extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.myapplication.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.example.myapplication.answer_shown";
    private static final String EXTRA_ANSWER_CHEATED = // ensure user doesn't waste more cheats
            "com.example.myapplication.answer_cheated";

    private static final String TAG = "CHEAT";

    private static final String KEY_CHEAT = "cheater";
    private static final int MAX_CHEATS = 3;
    private static int sCheatCount = 0;

    private TextView mAnswerTextView;
    private TextView mCheatHeaderView;
    private Button mShowAnswerButton;
    private boolean mIsAnswerTrue;
    private boolean mIsCheater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mCheatHeaderView = (TextView) findViewById(R.id.cheat_header_view);
        mIsAnswerTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mIsCheater = getIntent().getBooleanExtra(EXTRA_ANSWER_CHEATED, false);

        if(savedInstanceState != null){
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT, false);
            if(mIsCheater){
                setAnswerShownResult(true);
                mShowAnswerButton.setVisibility(View.INVISIBLE);
            }
        }

        if(mIsCheater){
            setAnswerView();
        }

        showAnswerButton();
        if(sCheatCount >= MAX_CHEATS || mIsCheater){
            mShowAnswerButton.setVisibility(View.INVISIBLE);
        }
        if(sCheatCount >= MAX_CHEATS){
            mCheatHeaderView.setText(R.string.all_cheats_used);
        }

    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue, boolean isCheated){
        Intent intent = new Intent(packageContext, Cheat.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        intent.putExtra(EXTRA_ANSWER_CHEATED, isCheated);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public static void resetCheatAmount(){
        sCheatCount = 0;
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    private void setAnswerView(){
        if(mIsAnswerTrue){
            mAnswerTextView.setText(R.string.true_button);
        }
        else{
            mAnswerTextView.setText(R.string.false_button);
        }
    }

    public void showAnswerButton(){
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswerShownResult(true);
                setAnswerView();
                mIsCheater = true;
                sCheatCount++;
                v.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_CHEAT, mIsCheater);
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
}
