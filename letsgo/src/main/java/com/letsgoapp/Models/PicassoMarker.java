package com.letsgoapp.Models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by normalteam on 07.02.17.
 */

public class PicassoMarker implements Target {
    final String TAG = "PicassoMarker";

    public Marker getmMarker() {
        return mMarker;
    }

    public void setmMarker(Marker mMarker) {
        this.mMarker = mMarker;
    }

    private Marker mMarker;
    private Integer id;
    private String url;
    private String colour;

    public PicassoMarker(Marker marker) {
        Log.d(TAG,"constructor");
        mMarker = marker;
    }

    @Override
    public int hashCode() {
        return mMarker.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof PicassoMarker) {
            Marker marker = ((PicassoMarker) o).mMarker;
            return mMarker.equals(marker);
        } else {
            return false;
        }
    }

    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
        Log.d(TAG,"onBitmapLoaded");
    }

    public void onBitmapFailed(Drawable errorDrawable) {
        Log.d(TAG,"onBitmapFailed");
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        Log.d(TAG,"onPrepareLoad");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

