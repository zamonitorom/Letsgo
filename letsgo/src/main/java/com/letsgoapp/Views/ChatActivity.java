package com.letsgoapp.Views;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
        ActivityChatBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        chatViewModel = new ChatViewModel(getIntent().getIntExtra("id", 0), getIntent().getStringExtra("slug"));
        binding.setChatVM(chatViewModel);
        SetTopContext(this);
        toolbar = binding.toolbar;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("onOptionsItemSelected", String.valueOf(id));
        if (id == R.id.action_remove_chat) {
            chatViewModel.removeChat();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}