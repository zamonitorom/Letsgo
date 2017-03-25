package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.letsgoapp.BR;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Views.SetMeetingActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 19.02.17.
 */

public class AddMeetingViewModel extends BaseObservable {

    private GoogleMap mMap;

    @Bindable
    public Boolean isButtonOnTop;

    private INavigationService navigationService;

    public AddMeetingViewModel() {
        isButtonOnTop = false;
    }

    public void setMap(GoogleMap googleMap){
        this.mMap = googleMap;

        final float initZoom = (float) 12.5;
        navigationService = new NavigationService();

        LatLng moscow = new LatLng(55.76, 37.61);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moscow, initZoom));
        mMap.setOnCameraMoveListener(() -> {
            isButtonOnTop = false;
            notifyPropertyChanged(BR.isButtonOnTop);
        });
        mMap.setOnCameraIdleListener(() -> {
            Log.d("addMap",mMap.getCameraPosition().toString()+"\n");
            isButtonOnTop = true;
            Observable.just(1)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .delay(950, TimeUnit.MILLISECONDS)
                    .subscribe((i)->{},throwable -> {},()->{
                        notifyPropertyChanged(BR.isButtonOnTop);
                    });

        });
    }

    public void click(){
        navigationService.goSetMeeting(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude);
    }
}
