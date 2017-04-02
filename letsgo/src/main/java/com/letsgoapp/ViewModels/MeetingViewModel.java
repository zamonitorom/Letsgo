package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.Photo;
import com.letsgoapp.R;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.Dialogs;
import com.letsgoapp.Views.ProfileActivity;
import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.entities.ZColor;

import java.util.ArrayList;

import in.myinnos.imagesliderwithswipeslibrary.SliderLayout;
import in.myinnos.imagesliderwithswipeslibrary.SliderTypes.BaseSliderView;
import in.myinnos.imagesliderwithswipeslibrary.SliderTypes.TextSliderView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 11.02.17.
 */

public class MeetingViewModel extends BaseObservable {
    private IDataService dataService;
    private String meetingTitle;
    private String meetingDescription;

    private SliderLayout demoSlider;

    private String about;
    private String userId;
    private String avatar;
    private String username;
    private String uhref;
    private Integer id;
    private ToolbarViewModel toolbarViewModel;

    @Bindable
    public ObservableArrayList<PhotoItemViewModel> photos = null;

    public ArrayList<String> photosFull;

    private INavigationService navigationService;

    public MeetingViewModel(String href) {
        toolbarViewModel = new ToolbarViewModel();
        photosFull = new ArrayList<>();
        photos = new ObservableArrayList<>();
        dataService = new APIService();
        navigationService = new NavigationService();
        loadData(href);
    }

    private void loadData(String reference) {
        dataService.getMeeting(reference)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meeting -> {
                            setId(meeting.getId());
                            setMeetingTitle(meeting.getTitle());
                            setMeetingDescription(meeting.getDescription());
                            setAvatar(meeting.getOwner().getAvatar());
                            setAbout(meeting.getOwner().getAbout());
                            setUsername(meeting.getOwner().getUsername());
                            setUhref(meeting.getOwner().getHref());
                            setUserId(meeting.getOwner().getId().toString());
                            toolbarViewModel.setToolbarTitle(meeting.getTitle());
//                            for (Photo photo : meeting.getOwner().getPhotos()) {
//                                addAbout(photo.getPhoto() + "\n");
//                                photos.add(new PhotoItemViewModel(photo.getPhoto()));
//                                photosFull.add(photo.getPhoto());
//                                Log.d("meetingActivity", photo.getPhoto());
//                            }
                            for (Photo photo : meeting.getOwner().getPhotos()) {
                                TextSliderView textSliderView = new TextSliderView((Activity) GetTopContext());
                                // initialize a SliderLayout
                                textSliderView
                                        .descriptionSize(20)
                                        .image(photo.getPhoto())
                                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
//                                        .setOnSliderClickListener((Activity) GetTopContext())
                                ;

                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle().putString("extra", "name");

                                demoSlider.addSlider(textSliderView);
                            }
                        }, throwable -> {
                            Log.d("meetingActivity", throwable.toString());
                            Dialogs dialogs = new Dialogs();
                            dialogs.ShowDialogAgree("Ошибка", "Не удалось отправить данные");
                        }

                        , () -> {
                            Log.d("meetingActivity", "complete\n");
                            Log.d("meetingActivity", photos.toString());
                        });
    }

    public void toUser() {
        navigationService.goProfile(getUhref());
    }

    public void close() {
        Activity activity = (Activity) GetTopContext();
        if (activity != null) {
            activity.finish();
        }
    }

    public void confirm() {
        dataService.sendConfirm(getId().toString(), new Object())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                }, throwable -> {
                    Log.d("meetingActivity", throwable.toString());
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось отправить данные");
                }, () -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("OK", "Запрос отправлен");
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

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public SliderLayout getDemoSlider() {
        return demoSlider;
    }

    public void setDemoSlider(SliderLayout demoSlider) {
        this.demoSlider = demoSlider;
    }
}
