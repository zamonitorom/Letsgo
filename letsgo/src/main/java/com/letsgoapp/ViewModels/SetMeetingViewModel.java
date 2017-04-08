package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.letsgoapp.BR;
import com.letsgoapp.Models.Coordinates;
import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Models.SendMeeting;
import com.letsgoapp.R;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.Dialogs;
import com.letsgoapp.Views.MainActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 28.02.17.
 */

public class SetMeetingViewModel extends BaseObservable{
    public MyObservableString title;
    public MyObservableString description;
    private IDataService apiService;
    private INavigationService navigationService;

    private double lat;
    private double lon;
    @Bindable
    public Boolean isFragment;

    public SetMeetingViewModel() {
        title = new MyObservableString();
        description = new MyObservableString();
        apiService = new APIService();
        navigationService = new NavigationService();

    }

    public void SendMeeting() {
        Activity activity = (Activity) GetTopContext();
        isFragment = true;
        notifyPropertyChanged(BR.isFragment);
        if (title.get().length() > 3 & description.get().length() > 10) {
            SendMeeting sendMeeting = new SendMeeting(title.get(), description.get(), new Coordinates(lat, lon),0);
            apiService.postMeeting(sendMeeting,
                    "application/json", String.valueOf(sendMeeting.toString().length()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                    }, throwable -> {
                        Dialogs dialogs = new Dialogs();
                        dialogs.ShowDialogAgree("Ошибка","Не удалось отправить данные");
                    }, () -> {
                        navigationService.goMainWithFinish();
                    });
        } else {
            Toast.makeText(activity, "Задайте заголовок и описание!", Toast.LENGTH_LONG).show();
        }

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
