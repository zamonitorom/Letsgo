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
//        getChats();
    }

    public void getChats(){
        if(items.size()>0){
            items.clear();
        }
        dataService.getChatList()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(chat->{
                    ChatsItemViewModel chatsItemViewModel = new ChatsItemViewModel();
                    if(chat.getLastMessage().getAuthor().getAvatar()!=null) {
                        chatsItemViewModel.setAvatar(chat.getLastMessage().getAuthor().getAvatar());
                    }else {
                        chatsItemViewModel.setAvatar(chat.getOwner().getAvatar());
                    }
                    chatsItemViewModel.setTitle(chat.getTitle());
                    chatsItemViewModel.setSlug(chat.getChannelSlug());
                    chatsItemViewModel.setLastMessage(chat.getLastMessage().getText());
                    chatsItemViewModel.setId(chat.getId());
                    items.add(chatsItemViewModel);
                })
                .subscribe(chat -> {},throwable -> {
                    throwable.printStackTrace();
                    throwable.printStackTrace();
                });
    }
    
    public void updateAvatars(){
        for (ChatsItemViewModel chatItemViewModel: items) {
            
        }
    }
}
