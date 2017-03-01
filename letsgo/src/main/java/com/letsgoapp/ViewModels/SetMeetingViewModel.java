package com.letsgoapp.ViewModels;

import com.google.gson.JsonObject;
import com.letsgoapp.Models.Coordinates;
import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Models.SendMeeting;
import com.letsgoapp.Services.APIService;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        JsonObject sendMeeting = new JsonObject();
        sendMeeting.addProperty("title",title.get());
        sendMeeting.addProperty("description",description.get());
        JsonObject coordinates = new JsonObject();
        coordinates.addProperty("lat",lat);
        coordinates.addProperty("lng",lon);
        sendMeeting.add("coordinates",coordinates);
        //SendMeeting sendMeeting = new SendMeeting(title.get(),description.get(),new Coordinates(lat,lon));
        apiService.postMeeting(sendMeeting, "Token 163df7faa712e242f7e6b4d270e29401e604b9b2",
                "application/json",String.valueOf(sendMeeting.toString().length()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }
}
