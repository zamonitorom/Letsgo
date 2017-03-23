package com.letsgoapp.Services;

import com.google.gson.JsonObject;
import com.letsgoapp.Models.EditableUser;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Owner;
import com.letsgoapp.Models.SendMeeting;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    Observable<Meeting> getMeeting(@Url String url,
                                   @Header("Authorization") String authorization);

    @POST("meetings-list/")
    Observable<Object> postMeeting(@Body Object sendMeeting,
                                   @Header("Authorization") String authorization,
                                   @Header("Content-Type") String contentType,
                                   @Header("Content-Length") String length);

    @GET
    Observable<Owner> getUser(@Url String url,
                              @Header("Authorization") String authorization);

    @PUT("user-detail/1/")
    Observable<Object> setUserData(@Body Object EditableUser,
                                   @Header("Authorization") String authorization,
                                   @Header("Content-Type") String contentType,
                                   @Header("Content-Length") String length);

    @Multipart
    @PUT("upload-photo/{adress}")
    Observable<ResponseBody> putPhoto(@Part MultipartBody.Part file,
                                      @Path("adress") String adress,
                                      @Header("Authorization") String authorization);
}
