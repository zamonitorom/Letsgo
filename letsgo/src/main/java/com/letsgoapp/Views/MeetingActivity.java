package com.letsgoapp.Views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.MeetingViewModel;
import com.letsgoapp.databinding.ActivityMeetingBinding;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;


public class MeetingActivity extends AppCompatActivity {
    Intent intent;
    MeetingViewModel meetingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTopContext(this);
        ActivityMeetingBinding activityMeetingBinding = DataBindingUtil.setContentView(this, R.layout.activity_meeting);
        intent = getIntent();
        meetingViewModel = new MeetingViewModel(intent.getExtras().getString("href"));
        activityMeetingBinding.setMeetingVM(meetingViewModel);
        activityMeetingBinding.setToolbar(meetingViewModel.getToolbarViewModel());
        meetingViewModel.setDemoSlider(activityMeetingBinding.slider);
        setSupportActionBar(activityMeetingBinding.tlbar);

    }

    @Override
    public void onResume(){
        super.onResume();
        SetTopContext(this);
    }

}
