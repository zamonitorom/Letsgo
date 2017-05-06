package com.letsgoapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocketMessage {

    @SerializedName("is_my")
    @Expose
    private Boolean isMy;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("href")
    @Expose
    private String href;

    public Boolean getIsMy() {
        return isMy;
    }

    public void setIsMy(Boolean isMy) {
        this.isMy = isMy;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
