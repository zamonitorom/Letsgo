package com.letsgoapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NewUser {

    @SerializedName("social_slug")
    @Expose
    private String socialSlug;
    @SerializedName("external_id")
    @Expose
    private Integer externalId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("first_name")
    @Expose
    private String firstName;

    /**
     * No args constructor for use in serialization
     */
    public NewUser() {
    }

    /**
     * @param token
     * @param firstName
     * @param externalId
     * @param socialSlug
     */
    public NewUser(String socialSlug, Integer externalId, String token, String firstName) {
        super();
        this.socialSlug = socialSlug;
        this.externalId = externalId;
        this.token = token;
        this.firstName = firstName;
    }

    public String getSocialSlug() {
        return socialSlug;
    }

    public void setSocialSlug(String socialSlug) {
        this.socialSlug = socialSlug;
    }

    public Integer getExternalId() {
        return externalId;
    }

    public void setExternalId(Integer externalId) {
        this.externalId = externalId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
