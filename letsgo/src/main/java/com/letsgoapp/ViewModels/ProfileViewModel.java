package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.letsgoapp.BR;
import com.letsgoapp.Models.EditableUser;
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
    public Boolean isMine;

    @Bindable
    public ObservableArrayList<PhotoItemViewModel> photos = null;

    private APIService dataService;

    public ProfileViewModel(String link) {
        photos = new ObservableArrayList<>();
        dataService = new APIService();
        loadData(link);
        isMine = false;
        notifyPropertyChanged(BR.isMine);
    }

    private void loadData(String link) {
        if (link != null) {
            if (!link.isEmpty()) {
                getUser(link);
                isMine = false;
                notifyPropertyChanged(BR.isMine);
            }else {
                getUser("http://185.76.147.143/user-detail/2/");
                isMine = true;
                notifyPropertyChanged(BR.isMine);
                EditableUser data = new EditableUser("testusernamename","testname","tesetabout");
                dataService.setUserData(data, "Token 163df7faa712e242f7e6b4d270e29401e604b9b2",
                        "application/json", String.valueOf(data.toString().length()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }
        }

    }

    private void getUser(String link){
        dataService.getUser(link,"Token 163df7faa712e242f7e6b4d270e29401e604b9b2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    setUsername(user.getUsername());
                    Log.d("getUser","username = "+ getUsername());
                    setAbout(user.getAbout());
                    Log.d("getUser","username = "+ getUsername());
                    setAvatar(user.getAvatar());
                    Log.d("getUser","username = "+ getUsername());
                    for (Photo photo : user.getPhotos()) {
                        photos.add(new PhotoItemViewModel(photo.getPhoto()));
                        Log.d("ProfileViewModel", photo.getPhoto());
                    }
                })
                .subscribe();
    }

//    Изменение юзера
//    PUT /user-detail/1/
//    Example request headers:
//    User-Agent: curl/7.35.0
//    Host: 185.76.147.143
//    Accept: */*
//Content-Type: application/json
//Authorization: Token 163df7faa712e242f7e6b4d270e29401e604b9b2
//Content-Length: 77
//
//{
//"username": "new_nick",
//"firs_name": "new first name",
//"about": "new about"
//}

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
