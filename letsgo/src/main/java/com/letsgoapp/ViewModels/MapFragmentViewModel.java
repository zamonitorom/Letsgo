package com.letsgoapp.ViewModels;

import android.content.Context;
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
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by normalteam on 06.02.17.
 */

public class MapFragmentViewModel {
    private APIService dataservice;

    public List<Meeting> meetingList = new ArrayList<>();
    private ArrayList<PicassoMarker> targetsList;
    public static final int AVATAR_SIZE = 100;

    private GoogleMap mMap;

    public MapFragmentViewModel(GoogleMap googleMap,Context context) {

        dataservice = new APIService();
        mMap = googleMap;
        final float initZoom = (float) 10.5;

        LatLng moscow = new LatLng(55.76, 37.61);
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
        targetsList = new ArrayList<>();
        getData(context);
    }

    public void getData(Context context) {
        dataservice.getMeetingList("Token 163df7faa712e242f7e6b4d270e29401e604b9b2")
                .subscribeOn(Schedulers.io())
                .flatMap(meetings -> Observable.from(meetings))
                .doOnNext(meeting -> Log.d("rx",String.valueOf(meeting.getId())))
                .doOnNext(meeting -> meetingList.add(meeting))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meeting -> {},throwable -> {}/*,()->callback.onResponse(new Object())*/,()->setMarkers(context));
    }


    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void setMarkers(Context context){
        if (meetingList != null) {

            for (Meeting m : meetingList) {
                MarkerOptions markerOne = new MarkerOptions().position(new LatLng(m.getCoordinates().getLat(),
                        m.getCoordinates().getLng()))/*.title("marker title")*/;

                PicassoMarker picassoMarker = new PicassoMarker(mMap.addMarker(markerOne));
                picassoMarker.getmMarker().setTag(m.getHref());
                if(m.getOwner().getAvatar()!=null) {
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
                if (pm.getUrl()!=null) {
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
