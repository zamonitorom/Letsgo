package com.letsgoapp.Views.Fragments;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.ChatsViewModel;
import com.letsgoapp.databinding.FragmentChatsBinding;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

/**
 * Created by normalteam on 29.04.17.
 */

public class ChatsFragment extends Fragment {

    private ChatsViewModel chatsViewModel;
    FragmentChatsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        binding.setChatVM(chatsViewModel);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chatsViewModel = new ChatsViewModel();
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_chats, container, false);
        View view = binding.getRoot();
        SetTopContext(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(chatsViewModel!=null){
            chatsViewModel.getChats();
        }
    }
}
