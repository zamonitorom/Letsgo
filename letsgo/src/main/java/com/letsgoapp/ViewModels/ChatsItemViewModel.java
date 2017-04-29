package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.letsgoapp.BR;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;

/**
 * Created by normalteam on 29.04.17.
 */

public class ChatsItemViewModel extends BaseObservable{
    private final String TAG = "ChatsItemViewModel";
    private INavigationService navigationService;
    private String title;
    private String lastMessage;
    private String avatar;
    private Integer id;

    public ChatsItemViewModel() {
        navigationService = new NavigationService();
    }

    public void click(){
        navigationService.goChat(1);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
        notifyPropertyChanged(BR.lastMessage);
    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
