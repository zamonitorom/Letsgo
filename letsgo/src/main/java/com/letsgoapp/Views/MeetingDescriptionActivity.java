package com.letsgoapp.Views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.MeetingViewModel;
import com.letsgoapp.ViewModels.ToolbarViewModel;
import com.letsgoapp.databinding.ActivityMeetingBinding;


public class MeetingDescriptionActivity extends AppCompatActivity {
    Intent intent;
    MeetingViewModel meetingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMeetingBinding activityMeetingBinding = DataBindingUtil.setContentView(this, R.layout.activity_meeting);
        intent = getIntent();
        meetingViewModel = new MeetingViewModel(intent.getExtras().getString("href"),this);

        meetingViewModel.getToolbarViewModel().setToolbar(activityMeetingBinding.tlbar);
        activityMeetingBinding.setToolbar(meetingViewModel.getToolbarViewModel());
        activityMeetingBinding.setMeetingVM(meetingViewModel);
        setSupportActionBar(meetingViewModel.getToolbarViewModel().getToolbar());
        meetingViewModel.getToolbarViewModel().getToolbar().setNavigationIcon(R.drawable.ic_close_dark);
        meetingViewModel.getToolbarViewModel().getToolbar().setNavigationOnClickListener(v -> finish());
    }

}
