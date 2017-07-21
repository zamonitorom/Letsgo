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
    public final String titleErrorMsg = "Введите не менее 4 символов";
    public final String descError = "Введите не менее 10 символов";
    private IDataService apiService;
    private INavigationService navigationService;

    private double lat;
    private double lon;
    @Bindable
    public Boolean isChecked = false;

    private Boolean error = false;
    private Boolean errorDesc = false;

    private String dateString = "Выберите дату";

    private PickedDate date;
    private Integer type;

    public SetMeetingViewModel() {
        date = new PickedDate();
        title = new MyObservableString(){
            @Override
            public void set(String value) {
                super.set(value);
                setError(false);
            }
        };
        description = new MyObservableString(){
            @Override
            public void set(String value) {
                super.set(value);
                setErrorDesc(false);
            }
        };
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
                if(title.get().length() <= 3){
                    setError(true);
                }

                if(description.get().length() <= 10){
                    setErrorDesc(true);
                }
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
        int mYear, mMonth, mDay;

        final Calendar cal = Calendar.getInstance();
        mYear = date.year.get();
        mMonth = date.month.get();
        mDay = date.day.get();

        DatePickerDialog datePickerDialog = new DatePickerDialog((Activity) ContextUtill.GetTopContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    getDate().year.set(year);
                    getDate().month.set(monthOfYear);
                    getDate().day.set(dayOfMonth);
                    setDateString("Дата:  "+getDate().toString());
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void changeLocation(){
        navigationService.goChangeLocation(lat,lon);
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

    @Bindable
    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
        notifyPropertyChanged(BR.error);
    }

    @Bindable
    public Boolean getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(Boolean errorDesc) {
        this.errorDesc = errorDesc;
        notifyPropertyChanged(BR.errorDesc);
    }

    @Bindable
    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
        notifyPropertyChanged(BR.dateString);
    }
}
