package com.letsgoapp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.letsgoapp.R;
import com.letsgoapp.Utils.VKHelper;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class LoginActivity extends AppCompatActivity {
    Intent answerIntent;
    VKHelper vkHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SetTopContext(this);
        vkHelper = new VKHelper();
        vkHelper.getLogin();
        answerIntent = new Intent();

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                vkHelper.getUserInfo(res);

                answerIntent.putExtra("auth", true);
                setResult(RESULT_OK, answerIntent);
                finish();
            }

            @Override
            public void onError(VKError error) {
                Log.d("LoginActivity: ", "onError"+error.errorMessage);
                Log.d("LoginActivity: ", "onError"+error.toString());
                answerIntent.putExtra("auth", false);
                vkHelper.getLogin();
                //finish();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
