package com.letsgoapp.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.letsgoapp.R;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.ViewModels.ImagePickViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.net.URI;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

//public class ImagePickActivity extends AppCompatActivity {
//
//
//    ImagePickViewModel imagePickViewModel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_pick);
//        SetTopContext(this);
//        imagePickViewModel = new ImagePickViewModel();
////        imagePickViewModel.getPicture();
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//
//        switch (requestCode) {
//            case GALLERY_REQUEST:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    imagePickViewModel.startCropper(selectedImage);
//                    break;
//                }
//            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
//                CropImage.ActivityResult result = CropImage.getActivityResult(imageReturnedIntent);
//                if (resultCode == RESULT_OK) {
//                    Uri resultUri = result.getUri();
//                    imagePickViewModel.sendPicture(resultUri);
//                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                    Exception error = result.getError();
//                }
//                break;
//        }
//    }
//}
