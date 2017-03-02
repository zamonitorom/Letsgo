package com.letsgoapp.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.letsgoapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void click(View view) {
        Intent answerIntent = new Intent();
        answerIntent.putExtra("auth",true);
        setResult(RESULT_OK,answerIntent);
        finish();
    }
}
