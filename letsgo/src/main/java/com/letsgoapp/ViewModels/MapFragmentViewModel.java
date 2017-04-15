package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.letsgoapp.BR;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Photo;
import com.letsgoapp.Models.PicassoMarker;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.CircleTransform;
import com.letsgoapp.Utils.Dialogs;
import com.letsgoapp.Utils.CoordinateService;
import com.letsgoapp.Views.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.myinnos.imagesliderwithswipeslibrary.SliderTypes.BaseSliderView;
import in.myinnos.imagesliderwithswipeslibrary.SliderTypes.TextSliderView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;


/**
 * Created by normalteam on 06.02.17.
 */

public class MapFragmentViewModel extends BaseObservable {

    private static final int AVATAR_SIZE = 100;
    private static final float initZoom = 10.5f;
    private final int RADIUS = 15;

    private IDataService dataservice;
    private INavigationService navigationService;

    private List<Meeting> meetingList = new ArrayList<>();
    private ArrayList<PicassoMarker> targetsList;
    private LatLng latLng;

    private GoogleMap mMap;
    @Bindable
    public Boolean isPreviewed;

    private Subscriber<Boolean> subscriber;

    private CoordinateService gpsProvider;
    private String preAva;
    private String currentRef;
    private Meeting currentMeeting;

    public MapFragmentViewModel(Subscriber<Boolean> subscriber) {
        this.subscriber = subscriber;
        gpsProvider = new CoordinateService();
    }

    public void setMap(GoogleMap googleMap) {

        Context context = (Context) GetTopContext();
        dataservice = new APIService();
        navigationService = new NavigationService();
        targetsList = new ArrayList<>();

        mMap = googleMap;

        latLng = gpsProvider.getLocation();

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, initZoom));
        mMap.setOnCameraChangeListener(cameraPosition -> {
            Log.d("mapFragment", "CHANDE+\n");
            checkAndUpdate(cameraPosition);

        });
        mMap.setOnMarkerClickListener(marker -> {
//            navigationService.goMeeting(marker.getId(), marker.getTag().toString());
            showPreview(marker);

            Log.d("mapFragment", "onMarkerClick+\n");
            return false;
        });
        getMeetings(context, String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
    }

    private void showPreview(Marker marker) {
        subscriber.onNext(false);
        String href = marker.getTag().toString();
        isPreviewed = true;
        dataservice.getMeeting(href)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meeting -> {
                            setCurrentMeeting(meeting);
                            setPreAva(meeting.getOwner().getAvatar());
                            setCurrentRef(meeting.getOwner().getHref());
                        },
                        throwable -> {
                        },
                        () -> {
                        });
        notifyPropertyChanged(BR.isPreviewed);

    }

    public void confirm() {
        dataservice.sendConfirm(getCurrentMeeting().getId().toString(), new Object())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                }, throwable -> {
                    Log.d("meetingActivity", throwable.toString());
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось отправить данные");
                }, () -> {
                    Dialogs dialogs = new Dialogs();
                    dialogs.ShowDialogAgree("OK", "Запрос отправлен");
                });
    }

    public void onAvaClick() {
        navigationService.goProfile(getCurrentRef());
    }

    public void closePreview() {
        isPreviewed = false;
        subscriber.onNext(true);
        notifyPropertyChanged(BR.isPreviewed);
    }

    public void toMyLocation() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsProvider.getLocation(), initZoom));
    }

    public void getData(Context context) {
        dataservice.getMeetingList()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .doOnNext(meeting -> Log.d("rx", String.valueOf(meeting.getId())))
                .doOnNext(meeting -> meetingList.add(meeting))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meeting -> {
                }, throwable -> {
                    Dialogs dialogs = new Dialogs();
                    Log.d("mapFragment", throwable.toString());
                    dialogs.ShowDialogAgree("Ошибка", "Не удалось загрузить данные");
                }, () -> setMarkers(context));
    }

    private void checkAndUpdate(CameraPosition cameraPosition) {
        LatLng current = cameraPosition.target;
        double cal = Math.sqrt((current.latitude - latLng.latitude) * (current.latitude - latLng.latitude)
                + (current.longitude - latLng.longitude) * (current.longitude - latLng.longitude));
        Log.d("mapFragment", String.valueOf(cal));
        if (cal > RADIUS * 0.01) {
            latLng = cameraPosition.target;
            getMeetings((Context) GetTopContext(), String.valueOf(current.latitude), String.valueOf(current.longitude));
        } else {
            Log.d("mapFragment", "false");
        }

    }

    private void getMeetings(Context context, String lat, String lng) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lat", lat);
        parameters.put("lng", lng);
        parameters.put("r", String.valueOf(RADIUS));
        dataservice.getLocalMeetingList(parameters)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .doOnNext(meeting -> Log.d("rx", String.valueOf(meeting.getColorStatus())))
                .doOnNext(meeting -> meetingList.add(meeting))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meeting -> {
                }, throwable -> {
                    try {
                        Dialogs dialogs = new Dialogs();
                        Log.d("mapFragment", throwable.toString());
                        dialogs.ShowDialogAgree("Ошибка", "Не удалось загрузить данные");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }, () -> setMarkers(context));
    }

    public void setMarkers(Context context) {
        if (meetingList != null) {
            try {
                for (Meeting m : meetingList) {
                    MarkerOptions markerOne = new MarkerOptions().position(new LatLng(m.getCoordinates().getLat(),
                            m.getCoordinates().getLng()));

                    PicassoMarker picassoMarker = new PicassoMarker(mMap.addMarker(markerOne));
                    picassoMarker.getmMarker().setTag(m.getHref());
                    if (m.getOwner().getAvatar() != null) {
                        try {
                            String avatar = URLDecoder.decode(m.getOwner().getAvatar(), "UTF-8");
                            picassoMarker.setUrl(avatar);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    if (m.getColorStatus().equals("disapproved")) {
                        picassoMarker.setColour(Color.YELLOW);
                    } else if (m.getColorStatus().equals("mine")) {
                        picassoMarker.setColour(Color.RED);
                    } else if (m.getColorStatus().equals("approved")) {
                        picassoMarker.setColour(Color.GREEN);
                    }

                    targetsList.add(picassoMarker);

                }
                for (PicassoMarker pm : targetsList) {
                    if (pm.getUrl() != null && !pm.getUrl().isEmpty()) {
                        Picasso.with(context)
                                .load(pm.getUrl())
                                .resize(AVATAR_SIZE, AVATAR_SIZE)
                                .centerCrop()
                                .transform(new CircleTransform(pm.getColour()))
                                .into(pm);
                    }
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }
    }

    @Bindable
    public String getPreAva() {
        return preAva;
    }

    public void setPreAva(String preAva) {
        this.preAva = preAva;
        notifyPropertyChanged(BR.preAva);
    }

    public String getCurrentRef() {
        return currentRef;
    }

    public void setCurrentRef(String currentRef) {
        this.currentRef = currentRef;
    }

    public Meeting getCurrentMeeting() {
        return currentMeeting;
    }

    public void setCurrentMeeting(Meeting currentMeeting) {
        this.currentMeeting = currentMeeting;
    }
}
