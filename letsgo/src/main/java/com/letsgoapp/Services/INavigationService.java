package com.letsgoapp.Services;

import android.net.Uri;

/**
 * Created by normalteam on 25.03.17.
 */

public interface INavigationService {
    void goSetMeeting(double latitude, double longitude);
    void goGalleryPick();
    void goCameraPick();
    void goCropper(int width,int height,Uri uri);
    void goProfile(String href);
    void goMeeting(String id,String href);
    void goMainWithFinish();
    void goLogin();
    void goMainFromLogin(String href,String token);
    void goFullscreen(int position);
    void goEdit();
    void goChat(Integer id,String slug,String title);
}
