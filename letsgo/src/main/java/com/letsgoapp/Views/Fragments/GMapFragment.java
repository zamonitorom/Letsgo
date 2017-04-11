package com.letsgoapp.Views.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.AddMeetingViewModel;
import com.letsgoapp.ViewModels.MapFragmentViewModel;
import com.letsgoapp.databinding.FragmentAddMeetingBinding;
import com.letsgoapp.databinding.FragmentMapBinding;

import rx.Subscriber;


public class GMapFragment extends Fragment {

    private MapFragment mapFragment;

    private MapFragmentViewModel mapFragmentViewModel;

    private Subscriber<Boolean> subscriber;

    public GMapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FloatingActionButton button = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        mapFragmentViewModel = new MapFragmentViewModel(subscriber);
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
        Log.d("onViewCreated",getActivity().getClass().toString());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Subscriber<Boolean> getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber<Boolean> subscriber) {
        this.subscriber = subscriber;
    }
}
