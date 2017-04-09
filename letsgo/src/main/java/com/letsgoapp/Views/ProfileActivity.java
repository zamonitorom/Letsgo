package com.letsgoapp.Views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.ProfileViewModel;
import com.letsgoapp.databinding.ActivityProfileBinding;
import com.theartofdev.edmodo.cropper.CropImage;

import static com.letsgoapp.Services.NavigationService.GALLERY_REQUEST;
import static com.letsgoapp.Services.NavigationService.REQUEST_IMAGE_CAPTURE;
import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding activityProfile2Binding;
    private ProfileViewModel profileViewModel;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityProfile2Binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        toolbar = activityProfile2Binding.toolbar;
        collapsingToolbarLayout = activityProfile2Binding.toolbarLayout;
        SetTopContext(this);
        profileViewModel = new ProfileViewModel(getIntent().getExtras().getString("link"));
        profileViewModel.setCollapsingToolbarLayout(collapsingToolbarLayout);
        activityProfile2Binding.setProfileVM(profileViewModel);
        activityProfile2Binding.content.setProfileVM(profileViewModel);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void onResume(){
        super.onResume();
        SetTopContext(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    profileViewModel.startCropper(selectedImage);
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(imageReturnedIntent);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    profileViewModel.sendPicture(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if(resultCode == RESULT_OK){
                    Bundle extras = imageReturnedIntent.getExtras();
                    profileViewModel.startCropper(extras);
                }
                break;
        }
    }

}
