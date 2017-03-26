package com.letsgoapp.Services;

import com.letsgoapp.Models.Confirm;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Owner;

import java.net.URI;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by normalteam on 26.03.17.
 */

public interface IDataService {
    Observable<List<Meeting>> getMeetingList(String authorization);
    Observable<List<Meeting>> getLocalMeetingList(Map<String, String> parameters, String authorization);
    Observable<Meeting> getMeeting(String url,String authorization);
    Observable<Object> postMeeting(Object sendMeeting, String authorization,
                                   String contentType,String length);
    Observable<Owner> getUser(String link, String authorization);
    Observable<Object> setUserData(Object editableUser, String authorization,
                                   String contentType, String length);
    Observable<ResponseBody> putPhoto(URI fileUri, String path, String authorization);
    Observable<Object> sendConfirm(String id,Object body,String authorization);
    Observable<List<Confirm>> getConfirms(String authorization);
}
