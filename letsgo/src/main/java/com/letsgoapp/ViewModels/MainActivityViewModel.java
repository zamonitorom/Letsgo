package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.ContextUtill;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 11.02.17.
 */

public class MainActivityViewModel extends BaseObservable {

    private String ava;
    private String name;
    private String unreadConfirms;

    private INavigationService navigationService;
    private IDataService dataService;

    private Subscriber<String> subscriber;

    public MainActivityViewModel(Subscriber<String> subscriber) {
        dataService = new APIService();
        navigationService = new NavigationService();
        this.subscriber = subscriber;
        if(ContextUtill.GetContextApplication()!=null) {
            getUser(ContextUtill.GetContextApplication().getHref());
        }
    }

    public void updateData(){
        if(ContextUtill.isDataChanged()) {
            if(ContextUtill.GetContextApplication()!=null) {
                getUser(ContextUtill.GetContextApplication().getHref());
            }
            ContextUtill.setDataChanged(false);
        }
    }

    public void getUnreadConfirm(){
        dataService.getUnreadConfirms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(unreadConfirm -> {
                    setUnreadConfirms(unreadConfirm.getData());
                    subscriber.onNext(getUnreadConfirms());
                });
    }
    private void getUser(String link) {
        Log.d("MainActivityViewModel", "getUser");
        dataService.getUser(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    ContextUtill.GetContextApplication().setUser(user);
                    setAva(user.getAvatar());
                    setName(user.getFirstName());
                })
                .subscribe(user -> {
                }, throwable -> {
                    Log.d("ProfileViewModel",throwable.toString());
                }, () -> {});
    }


    public void getProfile() {
        navigationService.goProfile(null);
    }

    @Bindable
    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
        notifyPropertyChanged(BR.ava);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getUnreadConfirms() {
        return unreadConfirms;
    }

    private void setUnreadConfirms(String unreadConfirms) {
        this.unreadConfirms = unreadConfirms;
        notifyPropertyChanged(BR.name);
    }
}
