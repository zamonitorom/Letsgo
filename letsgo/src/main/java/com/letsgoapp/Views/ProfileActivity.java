package com.letsgoapp.Views;

import android.databinding.DataBindingUtil;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.ProfileViewModel;
import com.letsgoapp.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding activityProfileBinding;
    private GestureDetectorCompat gestureDetector;
    public ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        gestureDetector = new GestureDetectorCompat(this, new LearnGesture());
        activityProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        profileViewModel = new ProfileViewModel(getIntent().getExtras().getString("link"));
        activityProfileBinding.setProfileVM(profileViewModel);
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
