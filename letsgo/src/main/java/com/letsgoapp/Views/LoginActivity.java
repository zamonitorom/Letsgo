package com.letsgoapp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.letsgoapp.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

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
        getLogin();
        answerIntent = new Intent();
    }

    @Override
    public void onBackPressed() {

    }

    private void getLogin() {
        VKSdk.login(this, sMyScope);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                getUserInfo(res);

                answerIntent.putExtra("auth", true);
                setResult(RESULT_OK, answerIntent);
                finish();
            }

            @Override
            public void onError(VKError error) {
                answerIntent.putExtra("auth", false);
                getLogin();
                //finish();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getUserInfo(VKAccessToken res) {
        VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONObject r = response.json.getJSONArray("response").getJSONObject(0);
                    answerIntent.putExtra("mail", res.email);
                    answerIntent.putExtra("vkId", res.userId);
                    answerIntent.putExtra("token", res.accessToken);
                    answerIntent.putExtra("firstName", r.getString("first_name"));
                    Log.d("response: ", r.toString());
                    Log.d("first_name", r.getString("first_name"));
                    Log.d("last_name", r.getString("last_name"));
                } catch (JSONException e) {
                    Log.d("progress", e.getMessage());
                }
                finish();
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
                Log.d("progress: ", progressType.toString());
            }

            @Override
            public void onError(VKError error) {
                Log.d("progress: ", "onError");
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            }
        });
    }

}
