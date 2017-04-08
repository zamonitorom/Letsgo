package com.letsgoapp.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import static android.content.Context.LOCATION_SERVICE;
import static com.letsgoapp.Utils.ContextUtill.GetTopContext;
import static com.letsgoapp.Views.LoginActivity.MY_PERMISSIONS;

/**
 * Created by normalteam on 26.03.17.
 */

public class CoordinateService {
    private LocationManager locationManager;

    private Location location;

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            Log.d("Gps","onLocationChanged");
            setLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("Gps","onProviderDisabled");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Gps","onProviderEnabled");
            Activity activity = (Activity) GetTopContext();
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                showLocation(locationManager.getLastKnownLocation(provider));
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Gps","onStatusChanged");
        }
    };

    public CoordinateService() {
        Activity activity = (Activity) GetTopContext();
        locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000 * 10, 10, locationListener);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                    locationListener);
            setLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        }
    }

    public LatLng getLocation() {
        if (location != null) {
            return new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            return new LatLng(55.76, 37.61);
        }
    }


    public void setLocation(Location location) {
        this.location = location;
    }
}