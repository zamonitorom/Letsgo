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
    private IAPIService iapiService;
    private IAPIService iapiService3;

    public APIService() {
        try {
            token = ContextUtill.GetContextApplication().getToken();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            String baseUrl = "http://37.46.128.134/";
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
            iapiService = retrofit.create(IAPIService.class);
            iapiService3 = postmeeting.create(IAPIService.class);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public Observable<List<Meeting>> getMeetingList() {
        return iapiService.getMeetingList(token);
    }

    public Observable<List<Meeting>> getLocalMeetingList(Map<String, String> parameters) {
        //7e4e5321b798715b30cf3e76f06dc588b7fd4bb6
        return iapiService.getLocalMeetingList(parameters, token);
    }

    public Observable<Meeting> getMeeting(String url) {
        return iapiService.getMeeting(url, token);
    }


    public Observable<Object> postMeeting(Object sendMeeting,
                                          String contentType, String length) {
        return iapiService3.postMeeting(sendMeeting, token, contentType, length);
    }

    public Observable<Owner> getUser(String link) {
        return iapiService3.getUser(link, token);
    }

    public Observable<Object> setUserData(Object editableUser,
                                          String contentType) {
        return iapiService3.setUserData(editableUser, token, contentType);
    }

    public Observable<PhotoAnswer> putPhoto(URI fileUri, String path) {

        File file = new File(fileUri);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);

        return iapiService3.putPhoto(requestFile, path, token);

    }

    public Observable<Object> sendConfirm(String id, Object object) {
        return iapiService3.sendConfirm(id, object, token);
    }

    @Override
    public Observable<List<Confirm>> getConfirms() {
        return iapiService.getConfirmList(token);
    }

    @Override
    public Observable<UserResponse> createUser(Object newUser) {
        return iapiService3.createUser(newUser,"application/json");
    }

    @Override
    public Observable<Object> sendApprove(String id,String status) {
        return iapiService3.sendConfirm(id,status,token);
    }

    @Override
    public Observable<Object> sendReject(String id,String status) {
        return iapiService3.sendReject(id,status,token);
    }

    @Override
    public Observable<UnreadConfirm> getUnreadConfirms() {
        return iapiService.getUnreadConfirms(token);
    }

    @Override
    public Observable<List<Chat>> getChatList(){
        return iapiService.getChatList(token);
    }

    @Override
    public Observable<List<Message>> getMessages(String id) {
        return iapiService.getMessages(id,token);
    }
}
