package com.letsgoapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by normalteam on 29.04.17.
 */

public class Message {

    @SerializedName("author")
    @Expose
    private Owner author;
    @SerializedName("date_create")
    @Expose
    private String dateCreate;
    @SerializedName("is_read")
    @Expose
    private Boolean isRead;
    @SerializedName("is_received")
    @Expose
    private Boolean isReceived;
    @SerializedName("text")
    @Expose
    private String text;

    public Owner getAuthor() {
        return author;
    }

    public void setAuthor(Owner author) {
        this.author = author;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(Boolean isReceived) {
        this.isReceived = isReceived;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}