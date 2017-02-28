
package com.letsgoapp.Models;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("photo")
    @Expose
    private String photo;

    private Bitmap bitmap;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Photo() {
    }

    /**
     * 
     * @param photo
     */
    public Photo(String photo) {
        super();
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
