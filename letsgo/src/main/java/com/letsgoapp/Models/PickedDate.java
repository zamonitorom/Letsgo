package com.letsgoapp.Models;

import android.databinding.ObservableInt;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by normalteam on 09.04.17.
 */

public class PickedDate {
    public final ObservableInt year = new ObservableInt();
    public final ObservableInt month = new ObservableInt();
    public final ObservableInt day = new ObservableInt();

    public PickedDate() {
        Calendar calendar = Calendar.getInstance();
        year.set(calendar.get(Calendar.YEAR));
        month.set(calendar.get(Calendar.MONTH));
        day.set(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void dateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year.set(year);
        this.month.set(monthOfYear);
        this.day.set(dayOfMonth);
    }

    @Override
    public String toString() {
        return String.valueOf(year.get())+"-"
                +String.valueOf(month.get()+1)+"-"
                +String.valueOf(day.get());
    }
}
