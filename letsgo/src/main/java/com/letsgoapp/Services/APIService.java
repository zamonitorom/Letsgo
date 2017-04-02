package com.letsgoapp.Services;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonObject;
import com.letsgoapp.Models.Confirm;
import com.letsgoapp.Models.EditableUser;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Owner;
import com.letsgoapp.Models.SendMeeting;
import com.letsgoapp.Utils.ContextUtill;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by normalteam on 06.02.17.
 */

public class APIService implements IDataService {

    private String baseUrl = "http://37.46.128.134/";
    private String token ;


    public APIService() {
        try {
            token = ContextUtill.GetContextApplication().getToken();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build();
    private IAPIService iapiService = retrofit.create(IAPIService.class);

    public Observable<List<Meeting>> getMeetingList() {
        return iapiService.getMeetingList(token);
    }

    public Observable<List<Meeting>> getLocalMeetingList(Map<String, String> parameters) {
        return iapiService.getLocalMeetingList(parameters, token);
    }

    public Observable<Meeting> getMeeting(String url) {
        return iapiService.getMeeting(url, token);
    }


    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s%s",
                    request.url(), chain.connection(), request.headers(), request.body().toString());

            if (request.method().compareToIgnoreCase("post") == 0) {
                requestLog = "\n" + requestLog + "\n";
            }

            Log.d("retrofit", "request" + "\n" + requestLog + "\n\n" + request.body());

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            @SuppressLint("DefaultLocale") String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("retrofit", "response" + "\n" + responseLog + "\n" + bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
            //return response;
        }
    }

    private OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();


    private Retrofit postmeeting = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build();

    private IAPIService iapiService3 = postmeeting.create(IAPIService.class);

    public Observable<Object> postMeeting(Object sendMeeting,
                                          String contentType, String length) {
        return iapiService3.postMeeting(sendMeeting, token, contentType, length);
    }

    public Observable<Owner> getUser(String link) {
        return iapiService.getUser(link, token);
    }

    public Observable<Object> setUserData(Object editableUser,
                                          String contentType, String length) {
        return iapiService3.setUserData(editableUser, token, contentType, length);
    }

    public Observable<ResponseBody> putPhoto(URI fileUri, String path) {
        /*
        ublic interface ApiInterface {
            @Multipart
            @POST("/api/Accounts/editaccount")
            Call<User> editUser (@Header("Authorization") String authorization, @Part("file\"; filename=\"pp.png\" ") RequestBody file , @Part("FirstName") RequestBody fname, @Part("Id") RequestBody id);
        }

        File file = new File(imageUri.getPath());
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), firstNameField.getText().toString());
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), AZUtils.getUserId(this));
        Call<User> call = client.editUser(AZUtils.getToken(this), fbody, name, id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(retrofit.Response<User> response, Retrofit retrofit) {
                AZUtils.printObject(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        */
        File file = new File(fileUri);

        // Создаем RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part используется, чтобы передать имя файла
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // Выполняем запрос
        return iapiService3.putPhoto(body, path, "image/jpg", token);

    }

    public Observable<Object> sendConfirm(String id, Object object) {
        return iapiService3.sendConfirm(id, object, token);
    }

    @Override
    public Observable<List<Confirm>> getConfirms() {
        return iapiService.getConfirmList("Token ee6d9b6dcdb03b6d7666c4cc14be644272e8c150");
    }
}
