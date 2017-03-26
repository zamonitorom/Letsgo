package com.letsgoapp.ViewModels;

import android.util.Log;

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

public class MyConfirmsViewModel {
    IDataService dataService;

    public List<Confirm> confirms;

    public MyConfirmsViewModel() {
        dataService = new APIService();
        confirms = new ArrayList<>();
        loadConfirms();
    }

    public void loadConfirms(){
        dataService.getConfirms("Token ee6d9b6dcdb03b6d7666c4cc14be644272e8c150")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .doOnNext(confirm -> {
                    Log.d("MyConfirmsViewModel",confirm.getDateCreate());
                })
                .subscribe(confirm -> {},throwable -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось загрузить данные");
                });
    }
}
