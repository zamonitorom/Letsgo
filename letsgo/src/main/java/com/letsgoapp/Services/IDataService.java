package com.letsgoapp.Services;

import com.letsgoapp.Models.Confirm;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Owner;
import com.letsgoapp.Models.UserResponse;

import java.net.URI;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by normalteam on 26.03.17.
 */

public interface IDataService {
    Observable<List<Meeting>> getMeetingList();
    Observable<List<Meeting>> getLocalMeetingList(Map<String, String> parameters);
    Observable<Meeting> getMeeting(String url);
    Observable<Object> postMeeting(Object sendMeeting,
                                   String contentType,String length);
    Observable<Owner> getUser(String link);
    Observable<Object> setUserData(Object editableUser,
                                   String contentType, String length);
    Observable<ResponseBody> putPhoto(URI fileUri, String path);
    Observable<Object> sendConfirm(String id,Object body);
    Observable<List<Confirm>> getConfirms();
    Observable<UserResponse>createUser(Object newUser);
}
