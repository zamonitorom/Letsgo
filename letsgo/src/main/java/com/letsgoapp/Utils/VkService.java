package com.letsgoapp.Utils;

import android.app.Activity;
import android.util.Log;

import com.letsgoapp.Models.NewUser;
import com.letsgoapp.Models.UserResponse;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 01.04.17.
 */

public class VkService {
    private static final String[] sMyScope = new String[]{
//            VKScope.FRIENDS,
//            VKScope.WALL,
//            VKScope.PHOTOS,
            VKScope.NOHTTPS,
//            VKScope.DOCS
    };

    private final String TAG= "VkService";
    private IDataService dataService;
    private INavigationService navigationService;
    private UserResponse responseUser;
    private Dialogs dialogs;

    public VkService() {
        dataService = new APIService();
        navigationService = new NavigationService();
        dialogs = new Dialogs();
    }

    public void getLogin() {
        if (GetTopContext() != null) {
            VKSdk.login((Activity) GetTopContext(), sMyScope);
        }
    }

    public void getUserInfo(VKAccessToken res) {
        NewUser user = new NewUser();
        user.setExternalId(Integer.valueOf(res.userId));
        user.setSocialSlug("vk");
        user.setToken(res.accessToken);
        user.setFirstName("  ");
        responseUser = new UserResponse();
        VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {

                try {
                    JSONObject r = response.json.getJSONArray("response").getJSONObject(0);

                    user.setFirstName(r.getString("first_name"));
                    Log.d("response: ", r.toString());
                    Log.d("first_name", r.getString("first_name"));
                    Log.d("last_name", r.getString("last_name"));
                    dataService.createUser(user)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(user -> {
                                Log.d(TAG, user.getHref());
                                responseUser=user;
                                Log.d(TAG, user.getToken());
                            }, throwable -> {
                                Log.d(TAG, throwable.toString());
                                dialogs.ShowDialogAgree("Ошибка", "Непредвиденная ошибка на стороне сервера. Повторите процедуру позднее");
                            },()->{
                                ContextUtill.GetContextApplication().setToken("Token "+responseUser.getToken());
                                ContextUtill.GetContextApplication().setHref(responseUser.getHref());
                                navigationService.goMainFromLogin(responseUser.getHref(),"Token "+responseUser.getToken());
                            });
                } catch (JSONException e) {
                    Log.d("progress", e.getMessage());
                }

            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
                Log.d("progress: ", progressType.toString());
            }

            @Override
            public void onError(VKError error) {
                Log.d("progress: ", "onError");
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            }
        });

    }

}
