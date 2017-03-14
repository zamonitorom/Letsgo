package com.letsgoapp.Services;

import android.util.Log;

import com.google.gson.JsonObject;
import com.letsgoapp.Models.EditableUser;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Owner;
import com.letsgoapp.Models.SendMeeting;


import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by normalteam on 06.02.17.
 */

public class APIService {

    private Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://185.76.147.143/")
            .build();
    private IAPIService iapiService = retrofit.create(IAPIService.class);

    public Observable<List<Meeting>> getMeetingList(String authorization) {
        return iapiService.getMeetingList(authorization);
    }

    private Retrofit getmeetimg = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://185.76.147.143/")
            .build();

    private IAPIService iapiService2 = getmeetimg.create(IAPIService.class);

    public Observable<Meeting> getMeeting(String url,String authorization) {
        return iapiService2.getMeeting(url,authorization);
    }


    public static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s%s",
                    request.url(), chain.connection(), request.headers(),request.body().toString());
            //YLog.d(String.format("Sending request %s on %s%n%s",
            //        request.url(), chain.connection(), request.headers()));
            if(request.method().compareToIgnoreCase("post")==0){
                requestLog ="\n"+requestLog+"\n";
            }
            Log.d("retrofit","request"+"\n"+requestLog);

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("retrofit","response"+"\n"+responseLog+"\n"+bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
            //return response;
        }
    }
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();


    private Retrofit postmeeting = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://185.76.147.143/")
            .client(client)
            .build();

    private IAPIService iapiService3 = postmeeting.create(IAPIService.class);

    public Observable<Object> postMeeting(Object sendMeeting, String authorization,
                                          String contentType,String length) {
        return iapiService3.postMeeting(sendMeeting,authorization,contentType,length);
    }


    private Retrofit getuser = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://185.76.147.143/")
            .build();

    private IAPIService iapiService4 = getuser.create(IAPIService.class);

    public Observable<Owner> getUser(String link,String authorization) {
        return iapiService4.getUser(link,authorization);
    }

    private IAPIService iapiService5 = getuser.create(IAPIService.class);

    public Observable<EditableUser> setUserData(Object editableUser, String authorization,
                                                String contentType, String length) {
        return iapiService4.setUserData(editableUser,authorization,contentType,length);
    }

}
