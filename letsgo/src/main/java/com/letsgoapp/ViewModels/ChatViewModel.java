package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.google.gson.Gson;
import com.letsgoapp.BR;
import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.Models.SocketMessage;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Utils.ContextUtill;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import rx.Observable;
import rx.Subscriber;
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
    private String slug;

    @Bindable
    public boolean isInput = false;

    private boolean emptyInput = true;

    public MyObservableString newMessage = new MyObservableString(){
        @Override
        public void set(String value) {
            super.set(value);
            if(value.length()==0){
                setEmptyInput(true);
            }else {
                setEmptyInput(false);
            }
        }
    };

    private Observable<String> chatObservable(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                URI uri;
                try {
                    String token = ContextUtill.GetContextApplication().getToken().replace("Token ","");
                    uri = new URI("ws://80.87.201.72/chat/" + slug + "/?token=" + token);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    return;
                }

                mWebSocketClient = new WebSocketClient(uri) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        Log.d(TAG, "Opened");
                    }

                    @Override
                    public void onMessage(String s) {
                        Log.d(TAG,"message" +s);
                        subscriber.onNext(s);
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        Log.d(TAG, "Closed " + s);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, "Error " + e.getMessage());
                    }
                };
                mWebSocketClient.connect();
            }
        });
    }

    @Bindable
    public ObservableArrayList<MessageViewModel> messages;
    public ChatViewModel(Integer id,String slug) {
        dataService = new APIService();
        messages = new ObservableArrayList<>();
        this.id = id;
        this.slug = slug;
        getMessages();
//        connectWebSocket();
        chatObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> {
                    SocketMessage message = new Gson().fromJson(s,SocketMessage.class);
                    messages.add(new MessageViewModel(message.getAuthorName(),message.getText(),
                            message.getIsMy(), message.getAvatar()));
                })
                .subscribe();
    }

    public void sendMessage(){
//        messages.add(new MessageViewModel("123",newMessage.get(),true));
        WebSocket socket =  mWebSocketClient.getConnection();
        mWebSocketClient.send(newMessage.get());
        newMessage.set("");

        /*
        try {
            if(mWebSocketClient!=null&&mWebSocketClient.getConnection().isConnecting()){
                mWebSocketClient.send(newMessage.get());
                newMessage.set("");
            }else {
                if(mWebSocketClient!=null){
                    mWebSocketClient.connect();
                }
            }
        }catch (Exception e){
            Dialogs.ShowMessage("Не удалось отправить сообщение.");
        }
*/
    }

    public void setInput(){
        isInput = true;
        notifyPropertyChanged(BR.isInput);
    }

    public void finish(){
        if(mWebSocketClient!=null){
            mWebSocketClient.close();
        }
    }

    public void removeChat(){
        if(mWebSocketClient!=null){
            mWebSocketClient.close();
        }
        dataService.deleteChat(id)
                .subscribeOn(Schedulers.io())
                .subscribe(message -> {},throwable -> {},()->{

                });
    }

    private void getMessages(){
        dataService.getMessages(String.valueOf(id))
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(chat->{
                    messages.add(new MessageViewModel(chat.getAuthor().getFirstName(),chat.getText(),
                            chat.getIsMy(), chat.getAuthor().getAvatar()));
                })
                .subscribe(message -> {},throwable -> {},()->{

                });
    }

    @Bindable
    public boolean getEmptyInput() {
        return emptyInput;
    }

    public void setEmptyInput(boolean emptyInput) {
        this.emptyInput = emptyInput;
        notifyPropertyChanged(BR.emptyInput);
    }
}