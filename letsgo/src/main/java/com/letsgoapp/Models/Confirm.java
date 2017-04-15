package com.letsgoapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Confirm {

    @SerializedName("date_create")
    @Expose
    private String dateCreate;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_approved")
    @Expose
    private Boolean isApproved;
    @SerializedName("is_rejected")
    @Expose
    private Boolean isRejected;
    @SerializedName("meeting")
    @Expose
    private Meeting meeting;
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     *
     */
    public Confirm() {
    }

    /**
     *
     * @param id
     * @param meeting
     * @param isRejected
     * @param isApproved
     * @param dateCreate
     * @param user
     */
    public Confirm(String dateCreate, Integer id, Boolean isApproved, Boolean isRejected, Meeting meeting, User user) {
        super();
        this.dateCreate = dateCreate;
        this.id = id;
        this.isApproved = isApproved;
        this.isRejected = isRejected;
        this.meeting = meeting;
        this.user = user;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}