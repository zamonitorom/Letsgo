package com.letsgoapp.ViewModels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.Confirm;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashMap;
import java.util.Map;

import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 26.03.17.
 */

public class ConfirmItemViewModel extends BaseObservable{

    @Bindable
    public Boolean isApproved;
    @Bindable
    public Boolean isRejected;
    private String link;
    private String meetingName;
    private String meetingDescription;
    private String userName;
    private String id;
    private Boolean isHidden;

    private IDataService dataService;

    ConfirmItemViewModel() {
        dataService = new APIService();
    }

    public ConfirmItemViewModel(String link,Boolean approved) {
        dataService = new APIService();
        setHidden(false);
        if (link != null) {
            this.link = link;
            this.isApproved = approved;
            Log.d("ConfirmItemViewModel",String.valueOf(isApproved));
            notifyChange();
        }
    }

    public void approve(){
        dataService.sendApprove(getId(),"True")
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {},throwable -> {},()->{
                    setHidden(true);
                });
    }

    public void reject(){
        dataService.sendReject(getId(),"True")
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {},throwable -> {},()->{
                    setHidden(true);
                });
    }

    @Bindable
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        notifyPropertyChanged(BR.link);
    }

    @Bindable
    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
        notifyPropertyChanged(BR.meetingName);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
        notifyPropertyChanged(BR.meetingDescription);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
        notifyPropertyChanged(BR.hidden);
    }
}
