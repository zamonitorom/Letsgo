package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.Confirm;

/**
 * Created by normalteam on 26.03.17.
 */

public class ConfirmItemViewModel extends BaseObservable{
    @Bindable
    public Boolean isApproved;
    @Bindable
    public Boolean isRejected;

    private String userAvatar;
    private String meetingTitle;
    public ConfirmItemViewModel(Confirm confirm) {
        isApproved = confirm.getIsApproved();
        Log.d("ConfirmItemViewModel",isApproved.toString());
        isRejected = confirm.getIsRejected();
        Log.d("ConfirmItemViewModel",isRejected.toString());
        setMeetingTitle(confirm.getMeeting().getTitle());
        Log.d("ConfirmItemViewModel",getMeetingTitle());
        setUserAvatar(confirm.getUser().getAvatar());
        Log.d("ConfirmItemViewModel",getUserAvatar());
        notifyChange();
    }

    @Bindable
    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        notifyPropertyChanged(BR.userAvatar);
    }

    @Bindable
    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
        notifyPropertyChanged(BR.meetingTitle);
    }
}
