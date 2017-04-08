package com.letsgoapp.ViewModels;

import com.letsgoapp.Utils.Dialogs;
import com.letsgoapp.Utils.VkService;
import com.vk.sdk.VKAccessToken;

/**
 * Created by normalteam on 02.04.17.
 */

public class LoginViewModel {

    private final String TAG= "LoginViewModel";
    private VkService vkHelper;

    private Dialogs dialogs;

    public LoginViewModel() {
        vkHelper = new VkService();
        dialogs = new Dialogs();
    }

    public void goVk(){
        vkHelper.getLogin();
    }

    public void retry(){
        dialogs.ShowDialogAgree("Необходимо авторизоваться","Пожалуйста, авторизуйтесь, используя социальную сеть");
//        vkHelper.getLogin();
    }

    public void setUserVk(VKAccessToken res){
        vkHelper.getUserInfo(res);
    }
}
