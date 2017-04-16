package com.letsgoapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meeting {

    @SerializedName("color_status")
    @Expose
    private String colorStatus;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("group_type")
    @Expose
    private Integer groupType;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("meeting_date")
    @Expose
    private String meetingDate;
    @SerializedName("category")
    @Expose
    private Integer meetingType;
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("subway")
    @Expose
    private Object subway;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * No args constructor for use in serialization
     *
     */
    public Meeting() {
    }

    /**
     *
     * @param id
     * @param groupType
     * @param title
     * @param subway
     * @param colorStatus
     * @param description
     * @param owner
     * @param meetingType
     * @param meetingDate
     * @param href
     * @param coordinates
     */
    public Meeting(String colorStatus, Coordinates coordinates, String description, Integer groupType, String href, Integer id, String meetingDate, Integer meetingType, Owner owner, Object subway, String title) {
        super();
        this.colorStatus = colorStatus;
        this.coordinates = coordinates;
        this.description = description;
        this.groupType = groupType;
        this.href = href;
        this.id = id;
        this.meetingDate = meetingDate;
        this.meetingType = meetingType;
        this.owner = owner;
        this.subway = subway;
        this.title = title;
    }

    public String getColorStatus() {
        return colorStatus;
    }

    public void setColorStatus(String colorStatus) {
        this.colorStatus = colorStatus;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
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

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public Integer getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(Integer meetingType) {
        this.meetingType = meetingType;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Object getSubway() {
        return subway;
    }

    public void setSubway(Object subway) {
        this.subway = subway;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}