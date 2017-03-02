package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.letsgoapp.Models.Photo;
import com.letsgoapp.Services.APIService;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 02.03.17.
 */

public class ProfileViewModel extends BaseObservable {
    private String avatar;
    private String username;
    private String about;
    private String uhref;

    @Bindable
    public ObservableArrayList<PhotoItemViewModel> photos = null;

    private APIService dataService;

    public ProfileViewModel() {

    }

    private void loadData(String reference) {
        photos = new ObservableArrayList<>();
        dataService = new APIService();

    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUhref() {
        return uhref;
    }

    public void setUhref(String uhref) {
        this.uhref = uhref;
    }
}
