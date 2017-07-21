package com.letsgoapp.Services;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.letsgoapp.Utils.ContextUtill;
import com.letsgoapp.Utils.ICallback;
import com.letsgoapp.Views.ChatActivity;
import com.letsgoapp.Views.FullScreenActivity;
import com.letsgoapp.Views.LoginActivity;
import com.letsgoapp.Views.MainActivity;
import com.letsgoapp.Views.MeetingActivity;
import com.letsgoapp.Views.ProfileActivity;
import com.letsgoapp.Views.ProfileEditActivity;
import com.letsgoapp.Views.SetMeetingActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 25.03.17.
 */

public class NavigationService implements INavigationService {
    public static final int GALLERY_REQUEST = 19985;
    public static final int REQUEST_IMAGE_CAPTURE = 19984;

    private String mCurrentPhotoPath;

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
    public void goCameraPick(ICallback callback) {
        Activity activity = (Activity) GetTopContext();
        Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        //content://com.letsgoapp.provider/external_files/Pictures/Instagram/IMG_20170706_220728.jpg
        ///storage/emulated/0
        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (activity != null) {
            if (photoPickerIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivityForResult(photoPickerIntent, REQUEST_IMAGE_CAPTURE);
                callback.onResponse(fileUri);
            }
        }
//        if (photoPickerIntent.resolveActivity(activity.getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(activity,
//                        "com.letsgoapp.provider",
//                        photoFile);
//                photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                activity.startActivityForResult(photoPickerIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
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
                    .setAutoZoomEnabled(false)
                    .setMultiTouchEnabled(true)
                    .setScaleType(CropImageView.ScaleType.FIT_CENTER)
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

    @Override
    public void goLogin() {
        Activity activity = (Activity) GetTopContext();
        Intent intent = new Intent(activity, LoginActivity.class);
        if (activity != null) {
            activity.startActivityForResult(intent, 0);
//            activity.finish();
        }
    }

    @Override
    public void goMainFromLogin(String href,String token) {
        Activity activity = (Activity) GetTopContext();
        Intent answerIntent = new Intent();
        answerIntent.putExtra("auth", true);
        answerIntent.putExtra("href",href);
        answerIntent.putExtra("token",token);
        if (activity != null) {
            activity.setResult(RESULT_OK, answerIntent);
            activity.finish();
        }
    }

    @Override
    public void goFullscreen(int position) {
        Activity activity = (Activity) GetTopContext();
        Intent intent = new Intent(activity, FullScreenActivity.class);
        intent.putExtra("position",position);
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    @Override
    public void goEdit() {
        Activity activity = (Activity) GetTopContext();
        Intent intent = new Intent(activity, ProfileEditActivity.class);
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    @Override
    public void goChat(Integer id,String slug,String title) {
        Activity activity = (Activity) GetTopContext();
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("slug",slug);
        intent.putExtra("title",title);
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    @Override
    public void goChangeLocation(double latitude, double longitude) {
        Activity activity = (Activity) GetTopContext();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("changeLocation",true);
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        if (Build.VERSION.SDK_INT < 23) {
            return Uri.fromFile(getOutputMediaFile(type));
        } else {
            return FileProvider.getUriForFile((Activity) ContextUtill.GetTopContext(),
                    ContextUtill.GetContextApplication().getApplicationContext().getPackageName() + ".provider", getOutputMediaFile(type));
        }
    }

    private static File getOutputMediaFile(int type) {
        final String IMAGE_DIRECTORY_NAME = "Instagram";
        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        File tmp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = ((Activity) GetTopContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
