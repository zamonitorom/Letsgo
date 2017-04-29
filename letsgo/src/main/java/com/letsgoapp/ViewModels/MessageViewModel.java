package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.letsgoapp.BR;

/**
 * Created by normalteam on 29.04.17.
 */

public class MessageViewModel extends BaseObservable {
    private String text;
    @Bindable
    public Boolean isMine;

    public MessageViewModel(String text, Boolean isMine) {
        setText(text);
        setMine(isMine);
    }

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    public void setMine(Boolean mine) {
        isMine = mine;
        notifyPropertyChanged(BR.isMine);
    }
}
