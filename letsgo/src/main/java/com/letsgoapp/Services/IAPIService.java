package com.letsgoapp.Services;

import com.google.gson.JsonObject;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Owner;
import com.letsgoapp.Models.SendMeeting;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by normalteam on 06.02.17.
 */

public interface IAPIService {

    @GET("meetings-list/")
    Observable<List<Meeting>> getMeetingList(@Header("Authorization") String authorization);

    @GET
    Observable<Meeting> getMeeting(@Url String url, @Header("Authorization") String authorization);

    @POST("meetings-list/")
    Observable<Object> postMeeting(@Body Object sendMeeting,
                                   @Header("Authorization") String authorization,
                                   @Header("Content-Type") String contentType,
                                   @Header("Content-Length") String length);

    @GET
    Observable<Owner> getUser(@Url String url,@Header("Authorization") String authorization);
}
