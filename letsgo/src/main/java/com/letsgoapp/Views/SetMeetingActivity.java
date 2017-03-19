package com.letsgoapp.Views;

import android.databinding.DataBindingUtil;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.SetMeetingViewModel;
import com.letsgoapp.databinding.ActivitySetMeetingBinding;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class SetMeetingActivity extends AppCompatActivity {
    SetMeetingViewModel setMeetingViewModel;

    ActivitySetMeetingBinding binding;
    Toolbar toolbar;
    private GestureDetectorCompat gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_meeting);
        gestureDetector = new GestureDetectorCompat(this, new LearnGesture());
        setMeetingViewModel = new SetMeetingViewModel();
        binding.setSetViewModel(setMeetingViewModel);
        toolbar= binding.toolbar;
        SetTopContext(this);

        toolbar.setTitle("Создание события");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_dark);
        toolbar.setNavigationOnClickListener(v -> finish());
        setMeetingViewModel.lat = getIntent().getExtras().getDouble("Lat");
        setMeetingViewModel.lon = getIntent().getExtras().getDouble("Lon");

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    class LearnGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e2.getX() > e1.getX()) {
                finish();
            }
            return true;
        }
    }
}
