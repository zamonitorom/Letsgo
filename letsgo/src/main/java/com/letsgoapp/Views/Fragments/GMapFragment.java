package com.letsgoapp.Views.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.AddMeetingViewModel;
import com.letsgoapp.ViewModels.MapFragmentViewModel;
import com.letsgoapp.databinding.FragmentAddMeetingBinding;
import com.letsgoapp.databinding.FragmentMapBinding;


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
        mapFragmentViewModel = new MapFragmentViewModel();
        FragmentMapBinding fragmentmapBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_map, container, false);
        View view = fragmentmapBinding.getRoot();
        fragmentmapBinding.setMapVM(mapFragmentViewModel);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {mapFragmentViewModel.setMap(googleMap);});

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
