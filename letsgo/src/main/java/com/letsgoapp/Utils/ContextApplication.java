package com.letsgoapp.Utils;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.letsgoapp.Models.Owner;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;

public class ContextApplication extends Application {

    private Object topActivity;
    private String token;
    private String href;
    private ArrayList<String> currentPhotos;
    private Owner user;

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        FirebaseApp.initializeApp(this);
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

    public ArrayList<String> getCurrentPhotos() {
        return currentPhotos;
    }

    public void setCurrentPhotos(ArrayList<String> currentPhotos) {
        this.currentPhotos = currentPhotos;
    }

    public Owner getUser() {
        return user;
    }

    public void setUser(Owner user) {
        this.user = user;
    }
}