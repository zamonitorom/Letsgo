package com.letsgoapp.Views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.ProfileViewModel;
import com.letsgoapp.databinding.ActivityProfileBinding;
import com.r0adkll.slidr.Slidr;

import java.io.IOException;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding activityProfile2Binding;
    public ProfileViewModel profileViewModel;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityProfile2Binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        toolbar = activityProfile2Binding.toolbar;
        setSupportActionBar(toolbar);

        SetTopContext(this);
        profileViewModel = new ProfileViewModel(getIntent().getExtras().getString("link"));
        activityProfile2Binding.setProfileVM(profileViewModel);
        activityProfile2Binding.content.setProfileVM(profileViewModel);
        toolbar.setTitle(profileViewModel.username.get());
//        Slidr.attach(this);

        //Сажин Ю.Б самохин кто то там методические рекомендации.
    }

}
