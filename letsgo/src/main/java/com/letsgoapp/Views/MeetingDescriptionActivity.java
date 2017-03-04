package com.letsgoapp.Views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.letsgoapp.R;
import com.letsgoapp.ViewModels.MeetingViewModel;
import com.letsgoapp.databinding.ActivityMeetingBinding;


public class MeetingDescriptionActivity extends AppCompatActivity {
    Intent intent;
    MeetingViewModel meetingViewModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMeetingBinding activityMeetingBinding = DataBindingUtil.setContentView(this, R.layout.activity_meeting);
        intent = getIntent();
        meetingViewModel = new MeetingViewModel(intent.getExtras().getString("href"),this);
        toolbar = activityMeetingBinding.tlbar;
        activityMeetingBinding.setMeetingVM(meetingViewModel);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_dark);
        toolbar.setNavigationOnClickListener(v -> finish());
//        meetingViewModel.getToolbarViewModel().getToolbar().setNavigationIcon(R.drawable.ic_close_dark);
//        meetingViewModel.getToolbarViewModel().getToolbar().setNavigationOnClickListener(v -> finish());
    }

}
