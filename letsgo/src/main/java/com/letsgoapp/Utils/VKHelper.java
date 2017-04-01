package com.letsgoapp.Utils;

import android.app.Activity;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 01.04.17.
 */

public class VKHelper {
    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.DOCS
    };


    public void getLogin() {
        if ((Activity) GetTopContext() != null) {
            VKSdk.login((Activity) GetTopContext(), sMyScope);
        }
    }

    public void getUserInfo(VKAccessToken res) {
        VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONObject r = response.json.getJSONArray("response").getJSONObject(0);
//                    answerIntent.putExtra("mail", res.email);
//                    answerIntent.putExtra("vkId", res.userId);
//                    answerIntent.putExtra("token", res.accessToken);
//                    answerIntent.putExtra("firstName", r.getString("first_name"));
                    Log.d("response: ", r.toString());
                    Log.d("first_name", r.getString("first_name"));
                    Log.d("last_name", r.getString("last_name"));
                } catch (JSONException e) {
                    Log.d("progress", e.getMessage());
                }
                if (((Activity) GetTopContext()) != null) {
                    ((Activity) GetTopContext()).finish();
                }
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
