
package com.letsgoapp.Models;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Photo {

    @SerializedName("delete_photo")
    @Expose
    private String deletePhoto;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("set_avatar")
    @Expose
    private String setAvatar;

    public String getDeletePhoto() {
        return deletePhoto;
    }

    public void setDeletePhoto(String deletePhoto) {
        this.deletePhoto = deletePhoto;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSetAvatar() {
        return setAvatar;
    }

    public void setSetAvatar(String setAvatar) {
        this.setAvatar = setAvatar;
    }

}
