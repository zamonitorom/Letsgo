package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.letsgoapp.BR;
import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Models.PickedDate;
import com.letsgoapp.Utils.ContextUtill;

import java.util.Calendar;

/**
 * Created by normalteam on 26.04.17.
 */

public class EditViewModel extends BaseObservable{
    private String dateText;
    private PickedDate date;
    public MyObservableString about;
    public MyObservableString firstName;

    public EditViewModel() {
        about = new MyObservableString();
        firstName = new MyObservableString();
        date = new PickedDate();
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
                    getDate().month.set(monthOfYear);
                    getDate().day.set(dayOfMonth);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void sendChanges(){

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
}
