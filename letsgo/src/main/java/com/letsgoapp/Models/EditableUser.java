package com.letsgoapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by normalteam on 14.03.17.
 */

public class EditableUser {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("first_name")
    @Expose
    private String firsName;
    @SerializedName("about")
    @Expose
    private String about;

    /**
     * No args constructor for use in serialization
     *
     */
    public EditableUser() {
    }

    /**
     *
     * @param username
     * @param about
     * @param firsName
     */
    public EditableUser(String username, String firsName, String about) {
        super();
        this.username = username;
        this.firsName = firsName;
        this.about = about;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
