package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.Confirm;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Utils.Dialogs;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 26.03.17.
 */

public class MyConfirmsViewModel extends BaseObservable{
    IDataService dataService;

    @Bindable
    public ObservableArrayList<ConfirmItemViewModel> confirms = null;

    @Bindable
    public ObservableArrayList<PhotoItemViewModel> photos = null;

    private Integer count=0;

    public MyConfirmsViewModel() {
        dataService = new APIService();
        confirms = new ObservableArrayList<>();
        photos = new ObservableArrayList<>();
        loadConfirms();
    }

    private void loadConfirms(){
        dataService.getConfirms("Token ee6d9b6dcdb03b6d7666c4cc14be644272e8c150")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(confirms1 -> {
                    Log.d("MyConfirmsViewModel",String.valueOf(confirms1.size()));
                    setCount(confirms1.size());
                    Log.d("MyConfirmsViewModel",confirms.toString());
                    for (Confirm confirm:confirms1) {
                        confirms.add(new ConfirmItemViewModel(confirm.getUser().getAvatar()));
                        photos.add(new PhotoItemViewModel(confirm.getUser().getAvatar()));
                    }
                    Log.d("MyConfirmsViewModel",confirms.toString());
                })
                .subscribe(confirm -> {},throwable -> {
                    Log.d("MyConfirmsViewModel",throwable.toString());
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось загрузить данные");
                });
    }

    @Bindable
    public String getCount() {
        return "You have "+ count.toString()+" confirms";
    }

    public void setCount(Integer count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }
}
