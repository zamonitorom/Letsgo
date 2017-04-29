package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.os.Build;
import android.util.Log;

import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Utils.ContextUtill;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

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

    public MyObservableString newMessage;

    @Bindable
    public ObservableArrayList<MessageViewModel> messages;
    public ChatViewModel(Integer id) {
        dataService = new APIService();
        messages = new ObservableArrayList<>();
        newMessage = new MyObservableString();
        this.id = id;
        getMessages();
        isConnected = false;
        connectWebSocket();
    }

    public void sendMessage(){
        if(mWebSocketClient!=null&&isConnected){
            mWebSocketClient.send(newMessage.get());
            newMessage.set("");
        }
        if(mWebSocketClient!=null&&!isConnected){
            mWebSocketClient.connect();
            mWebSocketClient.send(newMessage.get());
        }
    }

    private void connectWebSocket() {
        URI uri;
        try {
            String token = ContextUtill.GetContextApplication().getToken().replace("Token ","");
            uri = new URI("ws://37.46.128.134/chat/"+"qwerty"+"/?token="+token);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.d(TAG, "Opened");
                isConnected = true;
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
//                .doOnNext(Collections::reverse)
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(chat->{
                    messages.add(new MessageViewModel(chat.getAuthor().getFirstName(),chat.getText(),false));
                })
                .subscribe(message -> {},throwable -> {},()->{

                });
    }
}