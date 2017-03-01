package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.letsgoapp.Models.Coordinates;
import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Models.SendMeeting;
import com.letsgoapp.R;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Views.MainActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 28.02.17.
 */

public class SetMeetingViewModel {
    public MyObservableString title;
    public MyObservableString description;
    public ToolbarViewModel toolbarViewModel;
    private APIService apiService;

    public SetMeetingViewModel() {
        title = new MyObservableString();
        description = new MyObservableString();
        toolbarViewModel = new ToolbarViewModel();
        apiService = new APIService();

    }

    public double lat;
    public double lon;

    public void SendMeeting() {
        Activity activity = (Activity) GetTopContext();
        if (title.get().length() > 2 & description.get().length() > 10) {
            SendMeeting sendMeeting = new SendMeeting(title.get(), description.get(), new Coordinates(lat, lon));
            apiService.postMeeting(sendMeeting, "Token 163df7faa712e242f7e6b4d270e29401e604b9b2",
                    "application/json", String.valueOf(sendMeeting.toString().length()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                    }, throwable -> {
                    }, () -> {
                        Intent intent = new Intent(activity, MainActivity.class);
                        if (activity != null) {
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    });
        } else {
            Toast.makeText(activity, "Задайте заголовок и описание!", Toast.LENGTH_LONG).show();
        }

    }
}
