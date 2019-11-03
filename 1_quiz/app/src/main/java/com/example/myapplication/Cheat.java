package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Cheat extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.myapplication.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.example.myapplication.answer_shown";

    private static final String KEY_CHEAT = "cheater";

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private boolean mIsAnswerTrue;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mIsCheater = false;
        // TODO Figure out cheat (last page chap 5)
        if(savedInstanceState != null){
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT, false);
            if(mIsCheater){
                setAnswerShownResult(true);
            }
        }

        mIsAnswerTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsAnswerTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
                mIsCheater = true;
            }
        });
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, Cheat.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_CHEAT, mIsCheater);
    }
}
