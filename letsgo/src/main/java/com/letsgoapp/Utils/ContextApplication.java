package com.letsgoapp.Utils;

import android.app.Application;

import com.vk.sdk.VKSdk;

public class ContextApplication extends Application {

    private Object topActivity;
    private String token;

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
//        setToken("Token 163df7faa712e242f7e6b4d270e29401e604b9b2");
//        setToken("Token 09c6fc397cc3cf22b7056da065ee9d48fbacd680");
    }

    public Object getGlobalVarValue() {
        return topActivity;
    }

    public void setGlobalVarValue(Object value) {
        topActivity = value;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}