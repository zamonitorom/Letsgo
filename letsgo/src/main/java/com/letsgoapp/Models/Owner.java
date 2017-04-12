
package com.letsgoapp.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("birth_date")
    @Expose
    private String date;
    @SerializedName("gender")
    @Expose
    private Integer gender;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Owner() {
    }

    /**
     * 
     * @param photos
     * @param id
     * @param username
     * @param about
     * @param firstName
     * @param avatar
     * @param href
     */
    public Owner(String about, String avatar, String firstName, String href, Integer id, List<Photo> photos, String username,String date) {
        super();
        this.about = about;
        this.avatar = avatar;
        this.firstName = firstName;
        this.href = href;
        this.id = id;
        this.photos = photos;
        this.username = username;
        this.date = date;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
