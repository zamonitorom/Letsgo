package com.letsgoapp.Services;

import com.google.gson.JsonObject;
import com.letsgoapp.Models.Chat;
import com.letsgoapp.Models.Confirm;
import com.letsgoapp.Models.EditableUser;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Message;
import com.letsgoapp.Models.Owner;
import com.letsgoapp.Models.PhotoAnswer;
import com.letsgoapp.Models.SendMeeting;
import com.letsgoapp.Models.UnreadConfirm;
import com.letsgoapp.Models.UserResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by normalteam on 06.02.17.
 */

public interface IAPIService {

    @GET("meetings-list/")
    Observable<List<Meeting>> getMeetingList(@Header("Authorization") String authorization);

    @GET("meetings-list/")
    Observable<List<Meeting>> getLocalMeetingList(@QueryMap Map<String, String> parameters,
                                                  @Header("Authorization") String authorization);

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

    @PUT("user-detail/")
    Observable<Object> setUserData(@Body Object EditableUser,
                                   @Header("Authorization") String authorization);

    @PUT("upload-photo/{adress}")
    Observable<PhotoAnswer> putPhoto(@Body RequestBody file,
                                     @Path("adress") String adress,
                                     @Header("Authorization") String authorization);

    @POST("meeting-confirm/{id}/")
    Observable<Object> sendConfirm(@Path("id") String id,@Body Object object,
                                   @Header("Authorization") String authorization);

    @GET("confirms-list/")
    Observable<List<Confirm>> getConfirmList(@Header("Authorization") String authorization);

    @POST("api-token-auth/")
    Observable<UserResponse> createUser(@Body Object newUser,
                                        @Header("Content-Type") String contentType);
//                                   @Header("Content-Length") String length);
    @FormUrlEncoded
    @PUT("confirm-action/{id}/")
    Observable<Object> sendConfirm(@Path("id") String id,
                                        @Field("is_approved") String status,
                                        @Header("Authorization") String authorization);

    @FormUrlEncoded
    @PUT("confirm-action/{id}/")
    Observable<Object> sendReject(@Path("id") String id,
                                   @Field("is_rejected") String status,
                                   @Header("Authorization") String authorization);

    @GET("unread-confirms/")
    Observable<UnreadConfirm> getUnreadConfirms(@Header("Authorization") String authorization);

    @GET("chats-list/")
    Observable<List<Chat>> getChatList(@Header("Authorization") String authorization);

    @GET("message-list/{id}/")
    Observable<List<Message>> getMessages(@Path("id") String id,
                                          @Header("Authorization") String authorization);
    @FormUrlEncoded
    @PUT("set-client-key/")
    Observable<Object> sendToken(@Field("client_key") String key,
                                  @Header("Authorization") String authorization);

}
