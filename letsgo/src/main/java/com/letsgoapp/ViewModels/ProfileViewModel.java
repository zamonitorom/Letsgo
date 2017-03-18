package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.EditableUser;
import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Models.Photo;
import com.letsgoapp.Services.APIService;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 02.03.17.
 */

public class ProfileViewModel extends BaseObservable {
    private String avatar;
    public MyObservableString username;
    public MyObservableString about;
    //private String about;
    private String uhref;
    public MyObservableString firstName;
    @Bindable
    public Boolean isMine;
    @Bindable
    public Boolean isTouchable;

    @Bindable
    public ObservableArrayList<PhotoItemViewModel> photos = null;

    private APIService dataService;

    public ProfileViewModel(String link) {
        photos = new ObservableArrayList<>();
        dataService = new APIService();
        username = new MyObservableString();
        about = new MyObservableString();
        firstName = new MyObservableString();
        isMine = false;
        isTouchable = false;
        loadData(link);
        notifyPropertyChanged(BR.isMine);
        notifyPropertyChanged(BR.isTouchable);
    }

    private void loadData(String link) {
        if (link != null) {
            if (!link.isEmpty()) {
                getUser(link);
                isMine = false;
                notifyPropertyChanged(BR.isMine);
            } else {
                getUser("http://185.76.147.143/user-detail/1/");
                isMine = true;
                notifyPropertyChanged(BR.isMine);
//                EditableUser data = new EditableUser("testusernamename2", "testname2", "tesetabout2");
//                dataService.setUserData(data, "Token 163df7faa712e242f7e6b4d270e29401e604b9b2",
//                        "application/json", String.valueOf(data.toString().length()))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe();
            }
        }

    }

    private void getUser(String link) {
        dataService.getUser(link, "Token 163df7faa712e242f7e6b4d270e29401e604b9b2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    firstName.set(user.getFirstName());
                    username.set(user.getUsername());
                    Log.d("ProfileViewModel", "firstName = " + firstName.get());
                    about.set(user.getAbout());
                    Log.d("ProfileViewModel", "username = " + username.get());
                    setAvatar(user.getAvatar());
                    Log.d("ProfileViewModel", "about = " + about.get());
                    for (Photo photo : user.getPhotos()) {
                        photos.add(new PhotoItemViewModel(photo.getPhoto()));
                        //Log.d("ProfileViewModel", photo.getPhoto());
                    }
                })
                .subscribe();
    }

    public void fabClick(){
        Log.d("fabClick", "username = " + username.get()+" isMine = "+isMine.toString()+" isTouchable = "+isTouchable.toString());
        if(isMine) {
            isTouchable = true;
            notifyPropertyChanged(BR.isTouchable);
        }
        Log.d("fabClick2", "username = " + username.get()+" isMine = "+isMine.toString()+" isTouchable = "+isTouchable.toString());
    }

    public void sendChanges(){
        Log.d("ProfileViewModel", "sendChanges");
        if(isTouchable&isMine) {
            Log.d("ProfileViewModel", isMine.toString()+"  " + isTouchable.toString());
            EditableUser data = new EditableUser(username.get(), firstName.get(), about.get());
            Log.d("ProfileViewModel",username.get()+"  "+ firstName.get()+"  "+ about.get() );
            dataService.setUserData(data, "Token 163df7faa712e242f7e6b4d270e29401e604b9b2",
                    "application/json", String.valueOf(data.toString().length()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
        isTouchable = false;
        notifyPropertyChanged(BR.isTouchable);
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
    public String getUhref() {
        return uhref;
    }

    public void setUhref(String uhref) {
        this.uhref = uhref;
    }

}
