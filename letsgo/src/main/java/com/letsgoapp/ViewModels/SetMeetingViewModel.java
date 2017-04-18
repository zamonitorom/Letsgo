package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.letsgoapp.BR;
import com.letsgoapp.Models.Coordinates;
import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Models.PickedDate;
import com.letsgoapp.Models.SendMeeting;
import com.letsgoapp.R;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.ContextUtill;
import com.letsgoapp.Utils.Dialogs;
import com.letsgoapp.Views.MainActivity;

import java.util.Calendar;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 28.02.17.
 */

public class SetMeetingViewModel extends BaseObservable {
    public MyObservableString title;
    public MyObservableString description;
    private IDataService apiService;
    private INavigationService navigationService;

    private double lat;
    private double lon;
    @Bindable
    public Boolean isChecked = false;

    @Bindable
    public Boolean isDateChecking = false;

    private PickedDate date;

    private Integer type;

    public SetMeetingViewModel() {
        date = new PickedDate();
        title = new MyObservableString();
        description = new MyObservableString();
        apiService = new APIService();
        navigationService = new NavigationService();
    }

    public void sendMeeting() {
        Activity activity = (Activity) GetTopContext();
        if (isChecked) {
            if (title.get().length() > 3 & description.get().length() > 10) {
                String date = String.valueOf(getDate().year.get())+"-"
                        +String.valueOf(getDate().month.get()+1)+"-"
                        +String.valueOf(getDate().day.get());
                SendMeeting sendMeeting = new SendMeeting(title.get(), description.get(), new Coordinates(lat, lon), getType(),date);
                apiService.postMeeting(sendMeeting,
                        "application/json", String.valueOf(sendMeeting.toString().length()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> {
                        }, throwable -> {
                            Dialogs dialogs = new Dialogs();
                            dialogs.ShowDialogAgree("Ошибка", "Не удалось отправить данные");
                        }, () -> {
                            navigationService.goMainWithFinish();
                        });
            } else {
                Toast.makeText(activity, "Задайте заголовок и описание!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(activity, "Выберите тип события", Toast.LENGTH_LONG).show();
        }

    }

    public void click2(Integer integer) {
        Log.d("ViewModel", String.valueOf(integer));
        isChecked = true;
        setType(integer);
        notifyPropertyChanged(BR.isChecked);
    }

    public void openPicker(){
//        isDateChecking = true;
//        notifyPropertyChanged(BR.isDateChecking);

        int mYear, mMonth, mDay;

        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog((Activity) ContextUtill.GetTopContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    getDate().year.set(year);
                    getDate().month.set(monthOfYear);
                    getDate().day.set(dayOfMonth);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void closePicker(){

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public PickedDate getDate() {
        return date;
    }

    public void setDate(PickedDate date) {
        this.date = date;
    }
}
