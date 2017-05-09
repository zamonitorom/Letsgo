package com.letsgoapp.Views;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.letsgoapp.R;
import com.letsgoapp.ViewModels.ChatViewModel;
import com.letsgoapp.databinding.ActivityChatBinding;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

public class ChatActivity extends AppCompatActivity {
    private ChatViewModel chatViewModel;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChatBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);
        chatViewModel = new ChatViewModel(getIntent().getIntExtra("id",0),getIntent().getStringExtra("slug"));
        binding.setChatVM(chatViewModel);
        SetTopContext(this);
        toolbar= binding.toolbar;
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onStop() {
        chatViewModel.finish();
        super.onStop();
    }
}