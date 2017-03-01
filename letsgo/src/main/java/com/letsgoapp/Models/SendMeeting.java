package com.letsgoapp.Models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMeeting {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;

    /**
     * No args constructor for use in serialization
     *
     */
    public SendMeeting() {
    }

    /**
     *
     * @param title
     * @param description
     * @param coordinates
     */
    public SendMeeting(String title, String description, Coordinates coordinates) {
        super();
        this.title = title;
        this.description = description;
        this.coordinates = coordinates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

}