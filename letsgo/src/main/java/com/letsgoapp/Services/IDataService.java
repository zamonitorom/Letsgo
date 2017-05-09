package com.letsgoapp.Services;

import android.databinding.ObservableArrayList;

import com.letsgoapp.Models.Chat;
import com.letsgoapp.Models.Confirm;
import com.letsgoapp.Models.Meeting;
import com.letsgoapp.Models.Message;
import com.letsgoapp.Models.Owner;
import com.letsgoapp.Models.PhotoAnswer;
import com.letsgoapp.Models.UnreadConfirm;
import com.letsgoapp.Models.UserResponse;

import java.net.URI;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
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
    Observable<Object> setUserData(Object editableUser);
    Observable<PhotoAnswer> putPhoto(URI fileUri, String path);
    Observable<Object> sendConfirm(String id,Object body);
    Observable<List<Confirm>> getConfirms();
    Observable<UserResponse>createUser(Object newUser);
    Observable<Object> sendApprove(String id,String status);
    Observable<Object> sendReject(String id,String status);
    Observable<UnreadConfirm> getUnreadConfirms();
    Observable<List<Chat>> getChatList();
    Observable<List<Message>> getMessages(String id);
    Observable<Object> sendKey(String key);
//    Observable<RequestBody> sendReject();
}
