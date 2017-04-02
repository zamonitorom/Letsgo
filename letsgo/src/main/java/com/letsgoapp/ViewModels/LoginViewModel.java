package com.letsgoapp.ViewModels;

import android.content.Intent;
import android.util.Log;

import com.letsgoapp.Models.NewUser;
import com.letsgoapp.Models.UserResponse;
import com.letsgoapp.Services.APIService;
import com.letsgoapp.Services.IDataService;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.ContextUtill;
import com.letsgoapp.Utils.Dialogs;
import com.letsgoapp.Utils.VKHelper;
import com.vk.sdk.VKAccessToken;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by normalteam on 02.04.17.
 */

public class LoginViewModel {

    private final String TAG= "LoginViewModel";
    private VKHelper vkHelper;

    private Dialogs dialogs;

    private UserResponse response;

    public LoginViewModel() {
        vkHelper = new VKHelper();

        dialogs = new Dialogs();
    }

    public void goVk(){
        vkHelper.getLogin();
    }

    public void retry(){
        dialogs.ShowDialogAgree("Необходимо авторизоваться","Пожалуйста, авторизуйтесь, используя социальную сеть");
        vkHelper.getLogin();
    }

    public void setUserVk(VKAccessToken res){
        NewUser newUser = vkHelper.getUserInfo(res);
        response = new UserResponse();
    }
}
