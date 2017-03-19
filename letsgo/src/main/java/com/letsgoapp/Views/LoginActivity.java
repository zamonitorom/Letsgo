package com.letsgoapp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.letsgoapp.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivity extends AppCompatActivity {

    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.DOCS
    };

    Intent answerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        VKSdk.login(this,sMyScope);

        answerIntent = new Intent();
    }

//    public void click(View view) {
//        Intent answerIntent = new Intent();
//        answerIntent.putExtra("auth",true);
//        setResult(RESULT_OK,answerIntent);
//        finish();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                answerIntent.putExtra("token",res.accessToken);
                answerIntent.putExtra("auth",true);
                setResult(RESULT_OK, answerIntent);
                finish();
            }

            @Override
            public void onError(VKError error) {
                answerIntent.putExtra("auth", true);
                setResult(RESULT_OK, answerIntent);
                finish();
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
