package com.letsgoapp.Services;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.letsgoapp.Views.MainActivity;
import com.letsgoapp.Views.MeetingActivity;
import com.letsgoapp.Views.ProfileActivity;
import com.letsgoapp.Views.SetMeetingActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 25.03.17.
 */

public class NavigationService implements INavigationService {
    public static final int GALLERY_REQUEST = 19985;
    public static final int REQUEST_IMAGE_CAPTURE = 19984;

    @Override
    public void goSetMeeting(double latitude, double longitude) {
        Activity activity = (Activity) GetTopContext();
        Intent intent = new Intent(activity, SetMeetingActivity.class);
        intent.putExtra("Lat", latitude);
        intent.putExtra("Lon", longitude);
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    @Override
    public void goGalleryPick() {
        Activity activity = (Activity) GetTopContext();
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if (activity != null) {
            activity.startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        }
    }


    @Override
    public void goCameraPick() {
        Activity activity = (Activity) GetTopContext();
        Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (activity != null) {
            if (photoPickerIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivityForResult(photoPickerIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void goCropper(int width, int height, Uri uri) {
        Activity activity = (Activity) GetTopContext();
        if (activity != null) {
            CropImage.activity(uri)
                    .setMinCropResultSize(width, height)
                    .setMaxCropResultSize(width, height)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(activity);
        }

    }

    @Override
    public void goProfile(String href) {
        Activity activity = (Activity) GetTopContext();
        Intent intent = new Intent(activity, ProfileActivity.class);
        if (href != null) {
            intent.putExtra("link", href);
        } else {
            intent.putExtra("link", "");
        }
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    @Override
    public void goMeeting(String id, String href) {
        Activity activity = (Activity) GetTopContext();
        Intent meetingIntent = new Intent(activity, MeetingActivity.class);
        meetingIntent.putExtra("id",id);
        meetingIntent.putExtra("href",href);
        if (activity != null) {
            activity.startActivity(meetingIntent);
        }

    }

    @Override
    public void goMainWithFinish() {
        Activity activity = (Activity) GetTopContext();
        Intent intent = new Intent(activity, MainActivity.class);
        if (activity != null) {
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
