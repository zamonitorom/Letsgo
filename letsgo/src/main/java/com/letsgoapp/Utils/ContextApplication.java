package com.letsgoapp.Utils;

import android.app.Application;

public class ContextApplication extends Application {

    private Object topActivity;

    public Object getGlobalVarValue() {
        return topActivity;
    }

    public void setGlobalVarValue(Object value) {
        topActivity = value;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

}