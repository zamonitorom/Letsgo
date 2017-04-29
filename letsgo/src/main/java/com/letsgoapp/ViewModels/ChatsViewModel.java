package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 29.04.17.
 */

public class ChatsViewModel extends BaseObservable {
    private IDataService dataService;



    @Bindable
    public ObservableArrayList<ChatsItemViewModel> items;

    public ChatsViewModel() {
        items = new ObservableArrayList<>();
        dataService = new APIService();
        getChats();
    }

    public void getChats(){
        dataService.getChatList()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(chat->{
                    ChatsItemViewModel chatsItemViewModel = new ChatsItemViewModel();
                    chatsItemViewModel.setAvatar(chat.getOwner().getAvatar());
                    chatsItemViewModel.setTitle(chat.getTitle());
                    chatsItemViewModel.setLastMessage(chat.getLastMessage().getText());
                    items.add(chatsItemViewModel);
                })
                .subscribe();
    }
}
