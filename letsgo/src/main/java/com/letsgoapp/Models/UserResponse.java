package com.letsgoapp.Models;

/**
 * Created by normalteam on 02.04.17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("href")
    @Expose
    private String href;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserResponse() {
    }

    /**
     *
     * @param token
     * @param href
     */
    public UserResponse(String token, String href) {
        super();
        this.token = token;
        this.href = href;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}