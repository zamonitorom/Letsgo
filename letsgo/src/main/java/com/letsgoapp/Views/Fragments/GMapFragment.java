package com.letsgoapp.Views.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.MapFragmentViewModel;
import com.letsgoapp.Views.MeetingActivity;


public class GMapFragment extends Fragment {

    private MapFragment mapFragment;

    private MapFragmentViewModel mapFragmentViewModel;

    public GMapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(googleMap -> {
                    mapFragmentViewModel = new MapFragmentViewModel(googleMap, getActivity().getBaseContext());
                    mapFragmentViewModel.getmMap().setOnMarkerClickListener(marker -> {
                        Intent meetingIntent = new Intent(getActivity(), MeetingActivity.class);
                        meetingIntent.putExtra("id", marker.getId().toString());
                        meetingIntent.putExtra("href", marker.getTag().toString());
                        startActivity(meetingIntent);
                        Log.d("mapFragment", "onMarkerClick+\n");
                        return false;
                    });
                }
        );

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
