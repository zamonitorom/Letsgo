package com.letsgoapp.ViewModels;

import com.letsgoapp.Models.MyObservableString;

/**
 * Created by normalteam on 28.02.17.
 */

public class SetMeetingViewModel {
    public MyObservableString title;
    public MyObservableString description;
    public ToolbarViewModel toolbarViewModel;
    public SetMeetingViewModel() {
        title = new MyObservableString();
        description = new MyObservableString();
        toolbarViewModel = new ToolbarViewModel();

    }
    public double lat;
    public double lon;

    public void SendMeeting(){

    }
}
