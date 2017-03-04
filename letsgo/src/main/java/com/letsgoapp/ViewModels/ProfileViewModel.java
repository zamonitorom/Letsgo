package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.letsgoapp.BR;
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

    public ProfileViewModel(String link) {
        photos = new ObservableArrayList<>();
        dataService = new APIService();
        loadData(link);
    }

    private void loadData(String link) {
        if (link != null) {
            if (!link.isEmpty()) {
                dataService.getUser(link,"Token 163df7faa712e242f7e6b4d270e29401e604b9b2")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(user -> {
                            setUsername(user.getUsername());
                            setAbout(user.getAbout());
                            setAvatar(user.getAvatar());
                            for (Photo photo : user.getPhotos()) {
                                photos.add(new PhotoItemViewModel(photo.getPhoto()));
                                Log.d("ProfileViewModel", photo.getPhoto());
                            }
                        })
                        .subscribe();
            }
        }

    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
        notifyPropertyChanged(BR.about);
    }

    @Bindable
    public String getUhref() {
        return uhref;
    }

    public void setUhref(String uhref) {
        this.uhref = uhref;
    }
}
