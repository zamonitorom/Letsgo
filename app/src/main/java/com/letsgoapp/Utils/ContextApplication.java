package com.letsgoapp.Utils;

import android.app.Application;

import com.letsgoapp.Models.User;

public class ContextApplication extends Application {

    private Object topActivity;

    private User globalUser;

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

    public User getGlobalUser() {
        return globalUser;
    }

    public void setGlobalUser(User globalUser) {
        this.globalUser = globalUser;
    }
}