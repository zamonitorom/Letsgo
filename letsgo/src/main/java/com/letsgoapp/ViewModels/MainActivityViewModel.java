package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.Photo;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.ContextUtill;
import com.letsgoapp.Utils.Dialogs;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 11.02.17.
 */

public class MainActivityViewModel extends BaseObservable {

    private String ava;
    private String name;

    private INavigationService navigationService;
    private IDataService dataService;

    public MainActivityViewModel() {
        dataService = new APIService();
        navigationService = new NavigationService();
//        this.ava = "http://37.46.128.134/uploads/user-photos/66.jpg";
        getUser(ContextUtill.GetContextApplication().getHref());
    }

    public void updateData(){
        if(ContextUtill.isDataChanged()) {
            getUser(ContextUtill.GetContextApplication().getHref());
            ContextUtill.setDataChanged(false);
        }
    }

    private void getUser(String link) {
        Log.d("ProfileViewModel", "getUser");
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
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось загрузить данные");
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
}
