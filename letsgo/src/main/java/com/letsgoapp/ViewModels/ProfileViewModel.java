package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import com.letsgoapp.Models.PickedDate;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.ImagePickService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.ContextUtill;
import com.letsgoapp.Utils.Dialogs;
import com.letsgoapp.Utils.ICallback;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 02.03.17.
 */

public class ProfileViewModel extends BaseObservable {

    private final String TAG = "ProfileViewModel";
    private String avatar;
    private Integer icToolbar;
    private String uhref;
    private Uri fileUri;
    public MyObservableString about;
    public MyObservableString firstName;
    private Boolean editPhoto;
    @Bindable
    public Boolean isMine;
    @Bindable
    public Boolean isTouchable;



    @Bindable
    public ObservableArrayList<PhotoItemViewModel> photos = null;

    private PickedDate date;

    private IDataService dataService;

    public ImagePickService imagePickService;

    public ArrayList<String> images;

    private Subscriber<String> subscriber;

    public ProfileViewModel(String link, Subscriber<String> subscriber) {
        this.subscriber = subscriber;
        date = new PickedDate();
        images = new ArrayList<>();
        photos = new ObservableArrayList<>();
        dataService = new APIService();
        about = new MyObservableString();
        firstName = new MyObservableString();
        isMine = false;
        isTouchable = false;
        editPhoto = false;
        loadData(link);
        notifyPropertyChanged(BR.isMine);
        notifyPropertyChanged(BR.isTouchable);
        imagePickService = new ImagePickService();
    }

    private void loadData(String link) {
        if (link != null) {
            if (!link.isEmpty()) {
                getUser(link);
                isMine = false;
                notifyPropertyChanged(BR.isMine);
            } else {
                getUser(ContextUtill.GetContextApplication().getHref());
                isMine = true;
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
                    Log.d(TAG, "firstName = " + firstName.get());
                    about.set(user.getAbout());
                    setAvatar(user.getAvatar());
                    Log.d(TAG, "about = " + about.get());
                    String str_date=(String) user.getBirthDate();
                    subscriber.onNext(user.getFirstName());
                    int i = 0;
                    for (Photo photo : user.getPhotos()) {
                        PhotoItemViewModel model = new PhotoItemViewModel(photo.getPhoto(), new ICallback() {
                            @Override
                            public void onResponse(Object object) {
                                photos.remove((int) object);
                                images.remove((int) object);
                                refreshPositions(photos);
                            }
                        }, new ICallback() {
                            @Override
                            public void onResponse(Object object) {
                                setAvatar(photo.getPhoto());
                            }
                        });
                        model.setPosition(i);
                        model.setDeleteLink(photo.getDeletePhoto());
                        model.setAvatarLink(photo.getSetAvatar());
                        model.setEditable(false);
                        photos.add(model);
                        images.add(photo.getPhoto());
                        i++;
                        ContextUtill.setDataChanged(true);
                    }
                })
                .subscribe(user -> {
                }, throwable -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось загрузить данные");
                }, () -> {
                    ContextUtill.GetContextApplication().setCurrentPhotos(images);
                });
    }

    public void fabClick() {
        Log.d("fabClick",  " isMine = " + isMine.toString() + " isTouchable = " + isTouchable.toString());
        if (isMine) {
            isTouchable = true;
//            ContextUtill.setDataChanged(true);
            subscriber.onNext("Редактирование страницы");
            notifyPropertyChanged(BR.isTouchable);
        }
        Log.d("fabClick2", " isMine = " + isMine.toString() + " isTouchable = " + isTouchable.toString());
    }

    public void sendChanges() {
        //todo неправильная отправка даты
        Log.d("ProfileViewModel", "sendChanges");
        if (isTouchable && isMine) {
            subscriber.onNext(firstName.get());
            String date = String.valueOf(getDate().year.get())+"-"
                    +String.valueOf(getDate().month.get()+1)+"-"
                    +String.valueOf(getDate().day.get());
            Log.d("ProfileViewModel", isMine.toString() + "  " + isTouchable.toString());
            EditableUser data = new EditableUser(firstName.get(), firstName.get(), about.get());
            data.setDate(date);
            data.setGender(0);
            Log.d("ProfileViewModel", firstName.get() + "  " + about.get());
            dataService.setUserData(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data1 -> {
                    }, throwable -> {
                        Dialogs dialogs = new Dialogs();
                        dialogs.ShowDialogAgree("Ошибка", "Не удалось отправить данные");
                    });
        }
        isTouchable = false;
        ContextUtill.setDataChanged(true);
        notifyPropertyChanged(BR.isTouchable);
    }

    public void openPicker() {
        int mYear, mMonth, mDay;

        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog((Activity)ContextUtill.GetTopContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    getDate().year.set(year);
                    getDate().month.set(monthOfYear);
                    getDate().day.set(dayOfMonth);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void goEdit(){
        INavigationService navigationService = new NavigationService();
        navigationService.goEdit();
    }

    public void addPhotoGallery() {
        imagePickService.getPictureGallery();
    }

    public void addPhotoCamera() {
        imagePickService.getPictureCamera(new ICallback() {
            @Override
            public void onResponse(Object object) {
//                fileUri = String.valueOf(object);
                fileUri = (Uri) object;
            }
        });
    }

    public void setEditable(){
        editPhoto = !editPhoto;
        notifyPropertyChanged(BR.editPhoto);
        for (PhotoItemViewModel model: photos) {
            model.setEditable(editPhoto);
        }
    }

    public void startCropper(Uri uri) {
        imagePickService.startCropper(uri);
    }

    public void startCropper(Bundle bundle) {
        Bitmap imageBitmap = (Bitmap) bundle.get("data");
        imagePickService.startCropper(imageBitmap);
    }

    public void startCropper(String path) {
        //потому что ури приходит коллбэком
        if (path == null) {
//            path = fileUri;
        }
//        path = path.replace("/external_files","/storage/emulated/0");
        //content://com.letsgoapp.provider/external_files/Pictures/Instagram/IMG_20170706_220728.jpg
        ///storage/emulated/0
//        Uri uri = Uri.parse(path);
        imagePickService.startCropper(fileUri);
    }

    public void sendPicture(Uri uri) {
        URI uri2 = URI.create(uri.toString());
        String path = uri2.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            path = path.substring(cut + 1);
        }
        dataService.putPhoto(uri2, path)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(photoAnswer -> {
                    Photo answer = photoAnswer.getData();
                    Log.d(TAG, answer.getPhoto());
                    PhotoItemViewModel photoItemViewModel = new PhotoItemViewModel(answer.getPhoto(), new ICallback() {
                        @Override
                        public void onResponse(Object object) {
                            photos.remove((int) object);
                            images.remove((int) object);
                            refreshPositions(photos);
                        }
                    }, new ICallback() {
                        @Override
                        public void onResponse(Object object) {
                            setAvatar(answer.getPhoto());
                            ContextUtill.setDataChanged(true);
                        }
                    });
                    photoItemViewModel.setPosition(photos.size());
                    photoItemViewModel.setAvatarLink(answer.getSetAvatar());
                    photoItemViewModel.setDeleteLink(answer.getDeletePhoto());
                    photoItemViewModel.setEditable(false);
                    images.add(answer.getPhoto());
                    photos.add(photoItemViewModel);
                })
                .subscribe(responseBody -> {
                        },
                        throwable -> {
                            Dialogs dialogs = new Dialogs();
                            dialogs.ShowDialogAgree("Ошибка", "Не удалось отправить данные");
                        }, () -> {

                        });

    }

    private void refreshPositions(ObservableArrayList<PhotoItemViewModel> list){
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPosition(i);
        }
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

    public PickedDate getDate() {
        return date;
    }

    public void setDate(PickedDate date) {
        this.date = date;
    }

    @Bindable
    public Boolean getEditPhoto() {
        return editPhoto;
    }

    public void setEditPhoto(Boolean editable) {
        editPhoto = editable;
        notifyPropertyChanged(BR.editPhoto);
    }
}
