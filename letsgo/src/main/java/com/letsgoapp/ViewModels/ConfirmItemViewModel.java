package com.letsgoapp.ViewModels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.Confirm;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
    private String userName;

    ConfirmItemViewModel() {    }

    public ConfirmItemViewModel(String link,Boolean approved) {
        if (link != null) {
            this.link = link;
            this.isApproved = approved;
            Log.d("ConfirmItemViewModel",String.valueOf(isApproved));
            notifyChange();
        }
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
}
