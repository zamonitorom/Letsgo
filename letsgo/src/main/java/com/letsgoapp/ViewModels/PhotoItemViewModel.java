package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.letsgoapp.BR;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.Dialogs;
import com.letsgoapp.Utils.ICallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by normalteam on 15.02.17.
 */

public class PhotoItemViewModel extends BaseObservable {

    private String link;
    private String deleteLink;
    private String avatarLink;
    private Integer position;
    private ICallback deleteCallback;
    private ICallback avaCallback;
    private boolean editable;

    private IDataService dataService;

    public PhotoItemViewModel(String link, ICallback callback,ICallback avaCallback) {
        dataService = new APIService();
        this.deleteCallback = callback;
        this.avaCallback = avaCallback;
        if (link != null) {
            this.link = link;
            //loadBitmap(link);
        }
    }

    public PhotoItemViewModel(String link,Integer position) {
        if (link != null) {
            this.link = link;
            this.position = position;
        }
    }

    @Bindable
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        notifyPropertyChanged(BR.link);
    }

    public void click(){
        INavigationService navigationService = new NavigationService();
        navigationService.goFullscreen(position);
    }

    public void deletePhoto(){
        dataService.deletePhoto(deleteLink)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {},throwable -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось отправить данные");
                },()-> deleteCallback.onResponse(position));
    }

    public void setAvatar(){
        dataService.setAvatar(avatarLink)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {},throwable -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось отправить данные");
                },()-> avaCallback.onResponse(position));
    }

    @Bindable
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
        notifyPropertyChanged(BR.position);
    }

    public void setDeleteLink(String deleteLink) {
        this.deleteLink = deleteLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    @Bindable
    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyPropertyChanged(BR.editable);
    }
}
