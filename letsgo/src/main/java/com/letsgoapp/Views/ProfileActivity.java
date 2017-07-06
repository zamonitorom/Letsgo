package com.letsgoapp.Views;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.ProfileViewModel;
import com.letsgoapp.databinding.ActivityProfileBinding;
import com.theartofdev.edmodo.cropper.CropImage;

import rx.Subscriber;

import static com.letsgoapp.Services.NavigationService.GALLERY_REQUEST;
import static com.letsgoapp.Services.NavigationService.REQUEST_IMAGE_CAPTURE;
import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding activityProfile2Binding;
    private ProfileViewModel profileViewModel;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    private Subscriber subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            collapsingToolbarLayout.setTitle(s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityProfile2Binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        toolbar = activityProfile2Binding.toolbar;
        collapsingToolbarLayout = activityProfile2Binding.toolbarLayout;
        SetTopContext(this);
        profileViewModel = new ProfileViewModel(getIntent().getExtras().getString("link"),subscriber);
        activityProfile2Binding.setProfileVM(profileViewModel);
        activityProfile2Binding.content.setProfileVM(profileViewModel);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//        toolbar.setMenu();
        toolbar.setNavigationOnClickListener(v -> finish());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String fail = getIntent().getExtras().getString("link");
        if (fail != null && fail.isEmpty()) {
            getMenuInflater().inflate(R.menu.menu_profile, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("onOptionsItemSelected",String.valueOf(id));
        if (id == R.id.action_edit) {
            profileViewModel.goEdit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume(){
        super.onResume();
        SetTopContext(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        String picturePath = null;

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
//                    Bundle extras = imageReturnedIntent.getExtras();
//                    if (imageReturnedIntent != null) {
//                        Uri selectedImage = imageReturnedIntent.getData();
//                        String[] filePath = {MediaStore.Images.Media.DATA};
//                        Cursor c = this.getContentResolver().query(
//                                selectedImage, filePath, null, null, null);
//                        if (c == null) {
//                            picturePath = selectedImage.getPath();
//                        } else {
//                            c.moveToFirst();
//                            int columnIndex = c.getColumnIndex(filePath[0]);
//                            picturePath = c.getString(columnIndex);
//                            c.close();
//                        }
//                    }
                    profileViewModel.startCropper(picturePath);
                }
                break;
        }
    }



}
