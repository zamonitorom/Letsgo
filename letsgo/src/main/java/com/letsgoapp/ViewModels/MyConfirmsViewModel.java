package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.Confirm;
import com.letsgoapp.Models.NewUser;
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

public class MyConfirmsViewModel extends BaseObservable {
    private final String TAG= "MyConfirmsViewModel";
    IDataService dataService;

    @Bindable
    public ObservableArrayList<ConfirmItemViewModel> confirms = null;

    @Bindable
    public ObservableArrayList<PhotoItemViewModel> photos = null;

    private Integer count = 0;

    public MyConfirmsViewModel() {
        dataService = new APIService();
        confirms = new ObservableArrayList<>();
        photos = new ObservableArrayList<>();
        loadConfirms();
    }

    private void loadConfirms() {
//        dataService.getConfirms()
//                .subscribeOn(Schedulers.newThread())
//                .doOnNext(confirms1 -> {
//                    setCount(confirms1.size());
//                })
//                .flatMap(Observable::from)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(confirm -> {
//                    ConfirmItemViewModel confirmItemViewModel = new ConfirmItemViewModel();
//                    confirmItemViewModel.setLink(confirm.getUser().getAvatar());
//                    confirmItemViewModel.isApproved = confirm.getIsApproved();
//                    confirmItemViewModel.isRejected = confirm.getIsRejected();
//                    notifyChange();
//                    confirmItemViewModel.setMeetingName(confirm.getMeeting().getTitle());
//                    confirmItemViewModel.setUserName(confirm.getUser().getFirstName());
//                    Log.d("MyConfirmsViewModel", String.valueOf(confirmItemViewModel.isApproved));
//
//                    confirms.add(confirmItemViewModel);
//                    Log.d("MyConfirmsViewModel", String.valueOf(confirms.size()));
//                })
//                .subscribe(confirm -> {
//                }, throwable -> {
//                    Log.d("MyConfirmsViewModel", throwable.toString());
//                    Dialogs dialogs = new Dialogs();
//                    dialogs.ShowDialogAgree("Ошибка", "Не удалось загрузить данные");
//                });
    }

    @Bindable
    public String getCount() {
        return "You have " + count.toString() + " confirms";
    }

    public void setCount(Integer count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }
}
