package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.letsgoapp.BR;

/**
 * Created by normalteam on 11.02.17.
 */

public class MainActivityViewModel extends BaseObservable {

    private String mem;

    public MainActivityViewModel(String mem) {
        this.mem = mem;
    }

    @Bindable
    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
        notifyPropertyChanged(BR.mem);
    }
}
