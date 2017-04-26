package com.letsgoapp.Views;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.EditViewModel;
import com.letsgoapp.databinding.ActivityProfileEditBinding;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class ProfileEditActivity extends AppCompatActivity {
    private ActivityProfileEditBinding binding;
    private EditViewModel editViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_edit);
        editViewModel = new EditViewModel();
        binding.setEditVM(editViewModel);
        SetTopContext(this);
    }
}
