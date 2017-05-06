package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.EditableUser;
import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Models.PickedDate;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Utils.ContextUtill;
import com.letsgoapp.Utils.Dialogs;

import java.util.Calendar;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 26.04.17.
 */

public class EditViewModel extends BaseObservable {
    private final String TAG = "EditViewModel";
    private String dateText;
    private PickedDate date;
    public MyObservableString about;
    public MyObservableString firstName;
    @Bindable
    public Boolean gender;

    private IDataService dataService;

    public EditViewModel() {
        about = new MyObservableString();
        firstName = new MyObservableString();
        date = new PickedDate();
        dataService = new APIService();
        getUser(ContextUtill.GetContextApplication().getHref());
    }

    private void getUser(String link) {
        Log.d("ProfileViewModel", "getUser");
        dataService.getUser(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    firstName.set(user.getFirstName());
                    about.set(user.getAbout());
                    setDateText(user.getBirthDate());
                    if (user.getGender() == 1) {
                        setGender(true);
                    }
                    if (user.getGender() == 0) {
                        setGender(false);
                    }
                })
                .subscribe(user -> {
                }, throwable -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось загрузить данные");
                }, () -> {

                });
    }

    public void openPicker() {
        int mYear, mMonth, mDay;

        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog((Activity) ContextUtill.GetTopContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    getDate().year.set(year);
                    getDate().month.set(monthOfYear + 1);
                    getDate().day.set(dayOfMonth);
                    setDateText(String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth));
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void sendChanges() {
        String date = String.valueOf(getDate().year.get()) + "-"
                + String.valueOf(getDate().month.get() + 1) + "-"
                + String.valueOf(getDate().day.get());
        EditableUser data = new EditableUser(firstName.get(), firstName.get(), about.get());
        if (!data.equals(dateText)) {
            data.setDate(date);
        }else {
            data.setDate(dateText);
        }
        if(gender) {
            data.setGender(1);
        }
        if(!gender) {
            data.setGender(0);
        }
        Log.d(TAG, firstName.get() + "  " + about.get());
        dataService.setUserData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data1 -> {
                }, throwable -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось отправить данные");
                });

        ContextUtill.setDataChanged(true);
    }

    public void changeGender(Boolean b) {
        Log.d(TAG, String.valueOf(b));
        setGender(b);
//        setType(integer);
//        notifyPropertyChanged(BR.isChecked);
    }

    @Bindable
    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
        notifyPropertyChanged(BR.dateText);
    }

    public PickedDate getDate() {
        return date;
    }

    public void setDate(PickedDate date) {
        this.date = date;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }
}
