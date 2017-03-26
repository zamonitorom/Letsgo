package com.letsgoapp.ViewModels;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.PicassoMarker;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;


/**
 * Created by normalteam on 06.02.17.
 */

public class MapFragmentViewModel {
    private APIService dataservice;

    public List<Meeting> meetingList = new ArrayList<>();
    private LocationManager locationManager;
    private ArrayList<PicassoMarker> targetsList;
    private Location location;
    public static final int AVATAR_SIZE = 100;

    private GoogleMap mMap;

    public MapFragmentViewModel(GoogleMap googleMap/*, Context context*/) {

        Context context = (Context) GetTopContext();
        dataservice = new APIService();
        targetsList = new ArrayList<>();

        mMap = googleMap;
        final float initZoom = (float) 10.5;


        LatLng moscow = new LatLng(55.76, 37.61);

        mMap.getUiSettings().setZoomControlsEnabled(false);
//        mMap.getUiSettings().set

//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), initZoom));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moscow, initZoom));
        mMap.setOnCameraChangeListener(cameraPosition -> Log.d("mapFragment", "CHANDE+\n"));
//        mMap.setOnMarkerClickListener(marker -> {
//            Intent meetingIntent = new Intent(context, MeetingDescriptionActivity.class);
//            meetingIntent.putExtra("id",marker.getId().toString());
//            meetingIntent.putExtra("href",marker.getTag().toString());
//            context.startActivity(meetingIntent);
//            Log.d("mapFragment", "onMarkerClick+\n");
//            return false;
//        });
//        getData(context);
        getMeetings(context);
    }

    public void getData(Context context) {
        dataservice.getMeetingList("Token 163df7faa712e242f7e6b4d270e29401e604b9b2")
                .subscribeOn(Schedulers.io())
                .flatMap(meetings -> Observable.from(meetings))
                .doOnNext(meeting -> Log.d("rx", String.valueOf(meeting.getId())))
                .doOnNext(meeting -> meetingList.add(meeting))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meeting -> {
                }, throwable -> {
                }/*,()->callback.onResponse(new Object())*/, () -> setMarkers(context));
    }

    public void getMeetings(Context context){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lat", "55.76");
        parameters.put("lng", "37.61");
        parameters.put("r", "5");
        dataservice.getLocalMeetingList(parameters,"Token 163df7faa712e242f7e6b4d270e29401e604b9b2")
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .doOnNext(meeting -> Log.d("rx", String.valueOf(meeting.getId())))
                .doOnNext(meeting -> meetingList.add(meeting))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meeting -> {
                }, throwable -> {
                }/*,()->callback.onResponse(new Object())*/, () -> setMarkers(context));
    }


    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void setMarkers(Context context) {
        if (meetingList != null) {

            for (Meeting m : meetingList) {
                MarkerOptions markerOne = new MarkerOptions().position(new LatLng(m.getCoordinates().getLat(),
                        m.getCoordinates().getLng()))/*.title("marker title")*/;

                PicassoMarker picassoMarker = new PicassoMarker(mMap.addMarker(markerOne));
                picassoMarker.getmMarker().setTag(m.getHref());
                if (m.getOwner().getAvatar() != null) {
                    try {
                        String avatar = "";
                        avatar = URLDecoder.decode(m.getOwner().getAvatar(), "UTF-8");
                        picassoMarker.setUrl(avatar);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                targetsList.add(picassoMarker);

            }
            for (PicassoMarker pm : targetsList) {
                if (pm.getUrl() != null) {
                    Picasso.with(context)
                            .load(pm.getUrl())
                            .resize(AVATAR_SIZE, AVATAR_SIZE)
                            .centerCrop()
                            .transform(new CircleTransform())
                            .into(pm);
                }
            }
        }
    }
}
