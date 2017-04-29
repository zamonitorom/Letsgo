package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.os.Build;
import android.util.Log;

import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Utils.ContextUtill;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 29.04.17.
 */

public class ChatViewModel extends BaseObservable {
    private final String TAG = "ChatViewModel";
    private IDataService dataService;
    private WebSocketClient mWebSocketClient;
    private Integer id;
    private boolean isConnected;

    @Bindable
    public ObservableArrayList<MessageViewModel> messages;
    public ChatViewModel(Integer id) {
        dataService = new APIService();
        messages = new ObservableArrayList<>();
        this.id = id;
        getMessages();
        isConnected = false;
        connectWebSocket();
    }

    public void sendMessage(){
        if(mWebSocketClient!=null&&isConnected){
            mWebSocketClient.send("123");
        }
    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://37.46.128.134/chat/"+"qwerty"+"/?token=ee6d9b6dcdb03b6d7666c4cc14be644272e8c150");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.d(TAG, "Opened");
                isConnected = true;
//                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                Log.d(TAG,"message" +s);
//                ((Activity)ContextUtill.GetTopContext()).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TextView textView = (TextView)findViewById(R.id.messages);
//                        textView.setText(textView.getText() + "\n" + message);
//                    }
//                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.d(TAG, "Closed " + s);
                isConnected = false;
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    private void getMessages(){
        dataService.getMessages(String.valueOf(id))
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(chat->{
                    messages.add(new MessageViewModel(chat.getText(),false));
                })
                .subscribe();
    }
}
