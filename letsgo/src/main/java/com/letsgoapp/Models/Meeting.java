
package com.letsgoapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meeting {

    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("subway")
    @Expose
    private Object subway;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("color_status")
    @Expose
    private String colorStatus;
    @SerializedName("meeting_type")
    @Expose
    private Integer meetingType;
    @SerializedName("meeting_date")
    @Expose
    private Integer meetingDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public Meeting() {
    }

    /**
     *
     * @param id
     * @param title
     * @param subway
     * @param description
     * @param owner
     * @param href
     * @param coordinates
     */
    public Meeting(Coordinates coordinates, String description, String href, Integer id, Owner owner, Object subway, String title) {
        super();
        this.coordinates = coordinates;
        this.description = description;
        this.href = href;
        this.id = id;
        this.owner = owner;
        this.subway = subway;
        this.title = title;
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

    public String getColorStatus() {
        return colorStatus;
    }

    public void setColorStatus(String colorStatus) {
        this.colorStatus = colorStatus;
    }

    public Integer getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(Integer meetingType) {
        this.meetingType = meetingType;
    }

    public Integer getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Integer meetingDate) {
        this.meetingDate = meetingDate;
    }
}