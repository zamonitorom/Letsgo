package com.letsgoapp.ViewModels;

import android.content.Context;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import android.util.Log;


import com.letsgoapp.BR;
import com.letsgoapp.Models.Photo;
import com.letsgoapp.R;
import com.letsgoapp.Services.APIService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 11.02.17.
 */

public class MeetingViewModel extends BaseObservable {
    private APIService dataService;
    private String meetingTitle;
    private String meetingDescription;

    private String about;
    private String userId;
    private String avatar;
    private String username;
    private String uhref;

    private ToolbarViewModel toolbarViewModel;

    @Bindable
    public ObservableArrayList<PhotoItemViewModel> photos = null;

    //private Context context;

    public MeetingViewModel(String href, Context context) {
        //this.context = context;
        toolbarViewModel = new ToolbarViewModel();



        loadData(href);
    }

    private void loadData(String reference) {

        photos = new ObservableArrayList<>();
        dataService = new APIService();

        dataService.getMeeting(reference, "Token 163df7faa712e242f7e6b4d270e29401e604b9b2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meeting -> {
                    setMeetingTitle(meeting.getTitle());
                    setMeetingDescription(meeting.getDescription());
                    setAvatar(meeting.getOwner().getAvatar());
                    setAbout(meeting.getOwner().getAbout());
                    setUsername(meeting.getOwner().getUsername());
                    setUhref(meeting.getOwner().getHref());
                    setUserId(meeting.getOwner().getId().toString());
                    toolbarViewModel.setToolbarTitle(meeting.getTitle());
                    for (Photo photo : meeting.getOwner().getPhotos()) {
                        addAbout(photo.getPhoto() + "\n");
                        photos.add(new PhotoItemViewModel(photo.getPhoto()));
                        Log.d("meetingActivity", photo.getPhoto());
                    }
                }, throwable -> Log.d("meetingActivity", throwable.toString()), () -> {
                    Log.d("meetingActivity", "complete\n");
                    Log.d("meetingActivity", photos.toString());
                });
    }


    @Bindable
    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
        notifyPropertyChanged(BR.meetingTitle);
    }

    @Bindable
    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
        notifyPropertyChanged(BR.meetingDescription);
    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
        notifyPropertyChanged(BR.about);
    }

    public void addAbout(String s) {
        this.about += s;
        notifyPropertyChanged(BR.about);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getUhref() {
        return uhref;
    }

    public void setUhref(String uhref) {
        this.uhref = uhref;
        notifyPropertyChanged(BR.uhref);
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }

    public ToolbarViewModel getToolbarViewModel() {
        return toolbarViewModel;
    }

    public void setToolbarViewModel(ToolbarViewModel toolbarViewModel) {
        this.toolbarViewModel = toolbarViewModel;
    }
}
