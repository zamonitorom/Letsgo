package com.letsgoapp.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kic on 1/7/17.
 */
public class User implements Serializable {

    private Integer ID;
    private String userName;
    private String about;
    private String firstName;
    private String avatar;

    public User(JSONObject member) throws JSONException {
        ID = member.getInt("id");
        userName = member.getString("username");
        about = member.getString("about");
        firstName = member.getString("first_name");
        avatar = member.getString("avatar");
    }

    public String getAvatar() {
        return avatar;
    }
}
