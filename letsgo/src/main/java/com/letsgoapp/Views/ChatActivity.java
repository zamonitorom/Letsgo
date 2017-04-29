package com.letsgoapp.Views;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.ChatViewModel;
import com.letsgoapp.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChatBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);
        ChatViewModel chatViewModel = new ChatViewModel(getIntent().getIntExtra("id",0));
        binding.setChatVM(chatViewModel);
    }
}
