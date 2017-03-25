package com.letsgoapp.ViewModels;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.letsgoapp.Services.APIService;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.net.URI;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;
import static com.letsgoapp.Views.MainActivity.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

/**
 * Created by normalteam on 25.03.17.
 */

public class ImagePickViewModel {

    public static final int GALLERY_REQUEST = 19985;
    private static int IMG_HEIGTH = 1200;
    private static int IMG_WIDTH = 1200;

    Activity activity;
    public ImagePickViewModel() {
        activity = (Activity) GetTopContext();
    }
    public void getPicture(){
        if(activity!=null) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                activity.startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    public void startPicker(Uri uri){
        CropImage.activity(uri)
                .setMinCropResultSize(IMG_WIDTH,IMG_HEIGTH)
                .setMaxCropResultSize(IMG_WIDTH,IMG_HEIGTH)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);
    }

    public void sendPicture(Uri uri){
        URI uri2 = URI.create(uri.toString());
        APIService apiService= new APIService();
        String path= uri2.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            path = path.substring(cut + 1);
        }
        if (checkResolution(uri)){
            apiService.putPhoto(uri2,path,"Token 163df7faa712e242f7e6b4d270e29401e604b9b2")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }

    public boolean checkResolution(Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            if (bitmap.getHeight() == IMG_HEIGTH && bitmap.getWidth() == IMG_WIDTH) {
                return true;
            }
        }
        return false;
    }
}
