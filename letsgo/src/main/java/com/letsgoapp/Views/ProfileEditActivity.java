package com.letsgoapp.Views;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.letsgoapp.R;
import com.letsgoapp.databinding.ActivityProfileEditBinding;

public class ProfileEditActivity extends AppCompatActivity {
    private ActivityProfileEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_edit);
    }
}
