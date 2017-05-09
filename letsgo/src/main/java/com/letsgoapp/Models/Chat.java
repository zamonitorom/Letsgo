package com.letsgoapp.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("channel_slug")
    @Expose
    private String channelSlug;
    @SerializedName("last_message")
    @Expose
    private LastMessage lastMessage;
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("users")
    @Expose
    private List<Owner> users = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getChannelSlug() {
        return channelSlug;
    }

    public void setChannelSlug(String channelSlug) {
        this.channelSlug = channelSlug;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Owner> getUsers() {
        return users;
    }

    public void setUsers(List<Owner> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}