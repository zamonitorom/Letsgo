package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.letsgoapp.BR;

/**
 * Created by normalteam on 29.04.17.
 */

public class MessageViewModel extends BaseObservable {
    private String name;
    private String text;
    private String avatar;
    @Bindable
    public Boolean isMine;

    public MessageViewModel(String name, String text, Boolean isMine, String avatar) {
        setAvatar(avatar);
        setName(name);
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

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }
}
