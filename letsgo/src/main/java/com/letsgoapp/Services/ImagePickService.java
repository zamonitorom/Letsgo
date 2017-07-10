package com.letsgoapp.Services;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.letsgoapp.Models.Photo;
import com.letsgoapp.Utils.Dialogs;
import com.letsgoapp.Utils.ICallback;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetContextApplication;
import static com.letsgoapp.Utils.ContextUtill.GetTopContext;
import static com.letsgoapp.Views.LoginActivity.MY_PERMISSIONS;

/**
 * Created by normalteam on 25.03.17.
 */

public class ImagePickService {


    private static int IMG_HEIGHT = 2400;
    private static int IMG_WIDTH = 2400;

    Activity activity;

    private INavigationService navigationService;
    private IDataService apiService;

    public ImagePickService() {
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

    public void getPictureCamera(ICallback callback) {
        if (activity != null) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                navigationService.goCameraPick(callback);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS);
            }
        }
    }

    public void startCropper(Uri uri) {
//        Log.d("ImagePickService",uri.toString());
        navigationService.goCropper(IMG_WIDTH,IMG_HEIGHT,uri);
    }

    public void startCropper(Bitmap bitmap) {
        String path = activity.getExternalCacheDir().toString();
        OutputStream outputStream = null;
        File file = new File(path,"cachedImage.jpg");
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            Log.d("ImagePickService",file.getAbsolutePath());
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

//    public Photo sendPicture(Uri uri) {
//        URI uri2 = URI.create(uri.toString());
//        String path = uri2.getPath();
//        int cut = path.lastIndexOf('/');
//        if (cut != -1) {
//            path = path.substring(cut + 1);
//        }
//        Photo answer = new Photo();
//        apiService.putPhoto(uri2, path)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(photoAnswer -> answer.setPhoto(photoAnswer.getData().getHref()))
//                .subscribe(responseBody -> {},throwable -> {
//                    Dialogs dialogs = new Dialogs();
//                    dialogs.ShowDialogAgree("Ошибка","Не удалось отправить данные");
//                });
//        return answer;
//    }

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
