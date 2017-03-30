package com.letsgoapp.Views.Fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.MyConfirmsViewModel;
import com.letsgoapp.databinding.FragmentMyConfirmsBinding;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class MyConfirmsFragment extends Fragment {

    private MyConfirmsViewModel myConfirmsViewModel;
    FragmentMyConfirmsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        binding.setConfirmsVM(myConfirmsViewModel);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myConfirmsViewModel = new MyConfirmsViewModel();
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_my_confirms, container, false);
        View view = binding.getRoot();

        SetTopContext(getActivity());
        return view;
    }

}
