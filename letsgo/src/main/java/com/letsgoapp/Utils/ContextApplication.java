package com.letsgoapp.Utils;

import android.app.Application;

import com.vk.sdk.VKSdk;

public class ContextApplication extends Application {

    private Object topActivity;
    private String token;
    private String href;

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}