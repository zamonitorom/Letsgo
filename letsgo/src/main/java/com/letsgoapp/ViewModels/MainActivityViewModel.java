package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.letsgoapp.BR;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Views.ProfileActivity;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 11.02.17.
 */

public class MainActivityViewModel extends BaseObservable {

    private String mem;

    private INavigationService navigationService;
    public MainActivityViewModel(String mem) {
        this.mem = mem;
        navigationService = new NavigationService();
    }

    @Bindable
    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
        notifyPropertyChanged(BR.mem);
    }

    public void getProfile(){
        navigationService.goProfile(null);
    }

}
