package com.letsgoapp.Views;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.ChatViewModel;
import com.letsgoapp.databinding.ActivityChatBinding;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChatBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);
        ChatViewModel chatViewModel = new ChatViewModel(getIntent().getIntExtra("id",0),getIntent().getStringExtra("slug"));
        binding.setChatVM(chatViewModel);
        SetTopContext(this);
    }
}