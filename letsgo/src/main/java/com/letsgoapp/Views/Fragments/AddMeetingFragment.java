package com.letsgoapp.Views.Fragments;

import android.databinding.DataBindingUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;
import com.letsgoapp.R;
import com.letsgoapp.ViewModels.AddMeetingViewModel;
import com.letsgoapp.databinding.FragmentAddMeetingBinding;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

/**
 * Created by normalteam on 19.02.17.
 */

public class AddMeetingFragment extends Fragment {

    private MapFragment mapFragment;
    private AddMeetingViewModel addMeetingViewModel;

    public AddMeetingFragment(){
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_add);
        mapFragment.getMapAsync((googleMap)->addMeetingViewModel.setMap(googleMap));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Boolean value = getArguments().getBoolean("changing");
        Double lat = null;
        lat = getArguments().getDouble("Lat");
        Double lng = null;
        lng = getArguments().getDouble("Lon");
        addMeetingViewModel = new AddMeetingViewModel(value,lat,lng);
        FragmentAddMeetingBinding fragmentAddMeetingBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_meeting, container, false);
        View view = fragmentAddMeetingBinding.getRoot();
        fragmentAddMeetingBinding.setAddVM(addMeetingViewModel);
        SetTopContext(getActivity());
        return view;
    }

}
