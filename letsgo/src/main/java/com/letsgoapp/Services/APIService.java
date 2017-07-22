package com.letsgoapp.Services;

import com.letsgoapp.Models.Chat;
import com.letsgoapp.Models.Confirm;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Message;
import com.letsgoapp.Models.Owner;
import com.letsgoapp.Models.PhotoAnswer;
import com.letsgoapp.Models.UnreadConfirm;
import com.letsgoapp.Models.UserResponse;
import com.letsgoapp.Utils.ContextUtill;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by normalteam on 06.02.17.
 */

public class APIService implements IDataService {

    private String token ;
    private Api api;
    private Api api3;

    public APIService() {
        try {
            token = ContextUtill.GetContextApplication().getToken();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            String baseUrl = "http://80.87.201.72/";
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
            Retrofit postmeeting = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(client)
                    .build();
            api = retrofit.create(Api.class);
            api3 = postmeeting.create(Api.class);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public Observable<List<Meeting>> getMeetingList() {
        return api.getMeetingList(token);
    }

    public Observable<List<Meeting>> getLocalMeetingList(Map<String, String> parameters) {
        //7e4e5321b798715b30cf3e76f06dc588b7fd4bb6
        return api.getLocalMeetingList(parameters, token);
    }

    public Observable<Meeting> getMeeting(String url) {
        return api.getMeeting(url, token);
    }


    public Observable<Object> postMeeting(Object sendMeeting,
                                          String contentType, String length) {
        return api3.postMeeting(sendMeeting, token, contentType, length);
    }

    public Observable<Owner> getUser(String link) {
        return api3.getUser(link, token);
    }

    public Observable<Object> setUserData(Object editableUser) {
        return api3.setUserData(editableUser, token);
    }

    public Observable<PhotoAnswer> putPhoto(URI fileUri, String path) {

        File file = new File(fileUri);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);

        return api3.putPhoto(requestFile, path, token);

    }

    public Observable<Object> sendConfirm(String id, Object object) {
        return api3.sendConfirm(id, object, token);
    }

    @Override
    public Observable<List<Confirm>> getConfirms() {
        return api.getConfirmList(token);
    }

    @Override
    public Observable<UserResponse> createUser(Object newUser) {
        return api3.createUser(newUser,"application/json");
    }

    @Override
    public Observable<Object> sendApprove(String id,String status) {
        return api3.sendConfirm(id,status,token);
    }

    @Override
    public Observable<Object> sendReject(String id,String status) {
        return api3.sendReject(id,status,token);
    }

    @Override
    public Observable<UnreadConfirm> getUnreadConfirms() {
        return api3.getUnreadConfirms(token);
    }

    @Override
    public Observable<List<Chat>> getChatList(){
        return api3.getChatList(token);
    }

    @Override
    public Observable<List<Message>> getMessages(String id) {
        return api3.getMessages(id,token);
    }

    @Override
    public Observable<Object> sendKey(String key) {
        return api3.sendToken(key,token);
    }

    @Override
    public Observable<Object> deletePhoto(String url) {
        return api3.deletePhoto(url,token);
    }

    @Override
    public Observable<Object> setAvatar(String url) {
        return api3.setAvatar(url,token);
    }

    @Override
    public Observable<Object> deleteChat(Integer id) {
        return api3.deleteChat(id,token);
    }
}
