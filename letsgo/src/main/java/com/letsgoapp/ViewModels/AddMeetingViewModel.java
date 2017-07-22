package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.letsgoapp.BR;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 19.02.17.
 */

public class AddMeetingViewModel extends BaseObservable {

    private GoogleMap mMap;
    private Boolean isChanging = false;
    private double lat;
    private double lng;

    @Bindable
    public boolean isButtonOnTop;

    private INavigationService navigationService;

    public AddMeetingViewModel(Boolean value, double startLat, double startLng) {
        this.lat = startLat;
        this.lng = startLng;
        isChanging = value;
        isButtonOnTop = false;
    }

    public void setMap(GoogleMap googleMap) {
        this.mMap = googleMap;

        final float initZoom = (float) 12.5;
        navigationService = new NavigationService();
        LatLng moscow;
        if (isChanging) {
             moscow = new LatLng(55.76, 37.61);
        }else {
            moscow = new LatLng(lat,lng);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moscow, initZoom));
        mMap.setOnCameraMoveListener(() -> {
            isButtonOnTop = false;
            notifyPropertyChanged(BR.isButtonOnTop);
        });
        mMap.setOnCameraIdleListener(() -> {
            Log.d("addMap", mMap.getCameraPosition().toString() + "\n");
            isButtonOnTop = true;
            Observable.just(1)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .delay(950, TimeUnit.MILLISECONDS)
                    .subscribe((i) -> {
                    }, throwable -> {
                    }, () -> {
                        notifyPropertyChanged(BR.isButtonOnTop);
                    });

        });
    }

    public void click() {
        if (isChanging) {
            navigationService.goSetMeeting(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
        } else {
            navigationService.goSetFromChange(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
        }
    }
}
