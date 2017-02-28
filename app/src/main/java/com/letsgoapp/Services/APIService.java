package com.letsgoapp.Services;

import com.letsgoapp.Models.Meeting;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by normalteam on 06.02.17.
 */

public class APIService {

    Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://185.76.147.143/")
            .build();
    private IAPIService iapiService = retrofit.create(IAPIService.class);

    public Observable<List<Meeting>> getMeetingList(String authorization) {
        return iapiService.getMeetingList(authorization);
    }

    Retrofit getmeetimg = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://185.76.147.143/")
            .build();

    private IAPIService iapiService2 = getmeetimg.create(IAPIService.class);

    public Observable<Meeting> getMeeting(String url,String authorization) {
        return iapiService2.getMeeting(url,authorization);
    }

}
