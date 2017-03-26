package com.letsgoapp.ViewModels;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;
import static com.letsgoapp.Views.MainActivity.MY_PERMISSIONS;

/**
 * Created by normalteam on 25.03.17.
 */

public class ImagePickViewModel {


    private static int IMG_HEIGHT = 1200;
    private static int IMG_WIDTH = 1200;

    Activity activity;

    private INavigationService navigationService;
    private APIService apiService;

    public ImagePickViewModel() {
        activity = (Activity) GetTopContext();
        navigationService = new NavigationService();
        apiService = new APIService();
    }

    public void getPictureGallery() {
        if (activity != null) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                navigationService.goGalleryPick();
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS);
            }
        }
    }

    public void getPictureCamera() {
        if (activity != null) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                navigationService.goCameraPick();
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS);
            }
        }
    }

    public void startCropper(Uri uri) {
//        Log.d("ImagePickViewModel",uri.toString());
        navigationService.goCropper(IMG_WIDTH,IMG_HEIGHT,uri);
    }

    public void startCropper(Bitmap bitmap) {
        String path = activity.getExternalCacheDir().toString();
        OutputStream outputStream = null;
        File file = new File(path,"cachedImage.jpg");
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,85,outputStream);
            Log.d("ImagePickViewModel",file.getAbsolutePath());
            outputStream.flush();
            outputStream.close();
            Uri uri = Uri.fromFile(file);
            navigationService.goCropper(IMG_WIDTH,IMG_HEIGHT,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = activity.getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new  File(getImage.getPath(), "cameraResult.jpeg"));
        }
        return outputFileUri;
    }

    public void sendPicture(Uri uri) {
        URI uri2 = URI.create(uri.toString());
        String path = uri2.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            path = path.substring(cut + 1);
        }
//        if (checkResolution(uri)){
        apiService.putPhoto(uri2, path, "Token 163df7faa712e242f7e6b4d270e29401e604b9b2")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(responseBody -> {
                    try {
                        Log.d("ImagePickViewModel","responseBody"+ responseBody.string());
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        Integer status = jsonObject.getInt("status");
                        if (status != 204) {
                            Log.d("ImagePickViewModel","Successful");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                })
                .subscribe();
//        }
    }

    public boolean checkResolution(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            if (bitmap.getHeight() == IMG_HEIGHT && bitmap.getWidth() == IMG_WIDTH) {
                return true;
            }
        }
        return false;
    }
}
