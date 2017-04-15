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
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;

    /**
     * No args constructor for use in serialization
     */
    public Owner() {
    }

    /**
     * @param photos
     * @param id
     * @param about
     * @param gender
     * @param birthDate
     * @param firstName
     * @param avatar
     * @param href
     */
    public Owner(String about, String avatar, String birthDate, String firstName, Object gender, String href, Integer id, List<Photo> photos) {
        super();
        this.about = about;
        this.avatar = avatar;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.gender = gender;
        this.href = href;
        this.id = id;
        this.photos = photos;
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

    public Object getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
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

}