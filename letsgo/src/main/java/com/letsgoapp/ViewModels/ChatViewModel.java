package com.letsgoapp.ViewModels;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

/**
 * Created by normalteam on 29.04.17.
 */

public class ChatViewModel {
    private final String TAG = "ChatViewModel";
    private Integer id;

    @Bindable
    public ObservableArrayList<>
    public ChatViewModel(Integer id) {
        this.id = id;
    }
}
