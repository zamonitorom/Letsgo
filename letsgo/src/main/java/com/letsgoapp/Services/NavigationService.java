package com.letsgoapp.Services;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

import com.letsgoapp.Views.SetMeetingActivity;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 25.03.17.
 */

public class NavigationService implements INavigationService {
    public static final int GALLERY_REQUEST = 19985;
    public static final int REQUEST_IMAGE_CAPTURE = 19984;
    @Override
    public void goSetMeeting(double latitude, double longitude) {
        Activity activity=(Activity)GetTopContext();
        Intent intent = new Intent(activity, SetMeetingActivity.class);
        intent.putExtra("Lat",latitude);
        intent.putExtra("Lon",longitude);
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    @Override
    public void goGalleryPick() {
        Activity activity=(Activity)GetTopContext();
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if (activity != null) {
            activity.startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        }
    }


    @Override
    public void goCameraPick() {
        Activity activity=(Activity)GetTopContext();
        Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (activity != null) {
            if (photoPickerIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivityForResult(photoPickerIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
}
