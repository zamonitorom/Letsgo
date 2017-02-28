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
    private String avatar;

    public User(Integer ID, String userName, String about, String avatar) {
        this.ID = ID;
        this.userName = userName;
        this.about = about;
        this.avatar = avatar;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
