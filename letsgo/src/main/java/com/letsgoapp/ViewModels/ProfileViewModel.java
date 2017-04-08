package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.Models.EditableUser;
import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Models.Photo;
import com.letsgoapp.R;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.ImagePickService;
import com.letsgoapp.Utils.ContextUtill;
import com.letsgoapp.Utils.Dialogs;

import java.net.URI;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 02.03.17.
 */

public class ProfileViewModel extends BaseObservable {

    private final String TAG = "ProfileViewModel";
    private String avatar;
    private Integer icToolbar;
    public MyObservableString username;
    public MyObservableString about;
    private String uhref;
    public MyObservableString firstName;
    @Bindable
    public Boolean isMine;
    @Bindable
    public Boolean isTouchable;

    @Bindable
    public ObservableArrayList<PhotoItemViewModel> photos = null;

    private IDataService dataService;

    public ImagePickService imagePickViewModel;

    public ProfileViewModel(String link) {
        photos = new ObservableArrayList<>();
        dataService = new APIService();
        username = new MyObservableString();
        about = new MyObservableString();
        firstName = new MyObservableString();
        isMine = false;
        isTouchable = false;
        loadData(link);
        notifyPropertyChanged(BR.isMine);
        notifyPropertyChanged(BR.isTouchable);
        imagePickViewModel = new ImagePickService();
    }

    private void loadData(String link) {
        if (link != null) {
            if (!link.isEmpty()) {
                getUser(link);
                isMine = false;
                setIcToolbar(R.drawable.ic_message_white_36dp);
                notifyPropertyChanged(BR.isMine);
            } else {
                getUser(ContextUtill.GetContextApplication().getHref());
                isMine = true;
                setIcToolbar(R.drawable.ic_edit_white_36dp);
                notifyPropertyChanged(BR.isMine);
            }
        }

    }

    private void getUser(String link) {
        Log.d("ProfileViewModel", "getUser");
        dataService.getUser(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(user -> {
                    firstName.set(user.getFirstName());
                    username.set(user.getUsername());
                    Log.d(TAG, "firstName = " + firstName.get());
                    about.set(user.getAbout());
                    Log.d(TAG, "username = " + username.get());
                    setAvatar(user.getAvatar());
                    Log.d(TAG, "about = " + about.get());
                    for (Photo photo : user.getPhotos()) {
                        photos.add(new PhotoItemViewModel(photo.getPhoto()));
//                        Log.d("ProfileViewModel", photo.getPhoto());
                    }
                })
                .subscribe(user->{},throwable -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка","Не удалось загрузить данные");
                },()->{
                });
    }

    public void fabClick(){
        Log.d("fabClick", "username = " + username.get()+" isMine = "+isMine.toString()+" isTouchable = "+isTouchable.toString());
        if(isMine) {
            isTouchable = true;
            notifyPropertyChanged(BR.isTouchable);
        }
        Log.d("fabClick2", "username = " + username.get()+" isMine = "+isMine.toString()+" isTouchable = "+isTouchable.toString());
    }

    public void sendChanges(){
        Log.d("ProfileViewModel", "sendChanges");
        if(isTouchable && isMine) {
            Log.d("ProfileViewModel", isMine.toString()+"  " + isTouchable.toString());
            EditableUser data = new EditableUser(firstName.get(), firstName.get(), about.get());
            Log.d("ProfileViewModel",username.get()+"  "+ firstName.get()+"  "+ about.get() );
            dataService.setUserData(data,
                    "application/json")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data1->{},throwable -> {
                        Dialogs dialogs = new Dialogs();
                        dialogs.ShowDialogAgree("Ошибка","Не удалось отправить данные");
                    });
        }
        isTouchable = false;
        notifyPropertyChanged(BR.isTouchable);
    }

    public void addPhotoGallery(){
        imagePickViewModel.getPictureGallery();
    }

    public void addPhotoCamera(){
        imagePickViewModel.getPictureCamera();
    }

    public void startCropper(Uri uri){
        imagePickViewModel.startCropper(uri);
    }
    public void startCropper(Bundle bundle){
        Bitmap imageBitmap = (Bitmap) bundle.get("data");
        imagePickViewModel.startCropper(imageBitmap);
    }

    public void sendPicture(Uri uri){
        URI uri2 = URI.create(uri.toString());
        String path = uri2.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            path = path.substring(cut + 1);
        }
        Photo answer = new Photo();
        dataService.putPhoto(uri2, path)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(photoAnswer -> answer.setPhoto(photoAnswer.getData().getHref()))
                .subscribe(responseBody -> {},
                        throwable -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка","Не удалось отправить данные");
                },()->{
                            Log.d(TAG,answer.getPhoto());
                            photos.add(new PhotoItemViewModel(answer.getPhoto()));
                });

    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public String getUhref() {
        return uhref;
    }

    public void setUhref(String uhref) {
        this.uhref = uhref;
    }

    @Bindable
    public Integer getIcToolbar() {
        return icToolbar;
    }

    public void setIcToolbar(Integer icToolbar) {
        this.icToolbar = icToolbar;
        notifyPropertyChanged(BR.icToolbar);
    }
}
