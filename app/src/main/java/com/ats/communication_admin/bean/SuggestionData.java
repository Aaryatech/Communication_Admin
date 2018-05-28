package com.ats.communication_admin.bean;

import java.util.List;

/**
 * Created by MAXADMIN on 30/1/2018.
 */

public class SuggestionData {

    private int suggestionId;
    private String title;
    private String photo;
    private String description;
    private String date;
    private String time;
    private int frId;
    private String frName;
    private int isClosed;
    private List<SuggestionDetail> suggestionDetails;
    private int read;

    public SuggestionData() {
    }

    public SuggestionData(int suggestionId,String title, String photo, String description, int frId, int isClosed) {
        this.suggestionId = suggestionId;
        this.title = title;
        this.photo = photo;
        this.description = description;
        this.frId = frId;
        this.isClosed = isClosed;
    }

    public SuggestionData(int suggestionId, String title, String photo, String description, String date, String time, int frId, int isClosed) {
        this.suggestionId = suggestionId;
        this.title = title;
        this.photo = photo;
        this.description = description;
        this.date = date;
        this.time = time;
        this.frId = frId;
        this.isClosed = isClosed;
    }

    public SuggestionData(int suggestionId, String title, String photo, String description, String date, String time, int frId, String frName, int isClosed, int read) {
        this.suggestionId = suggestionId;
        this.title = title;
        this.photo = photo;
        this.description = description;
        this.date = date;
        this.time = time;
        this.frId = frId;
        this.frName = frName;
        this.isClosed = isClosed;
        this.read = read;
    }

    public SuggestionData(int suggestionId) {
        this.suggestionId = suggestionId;
    }

    public int getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFrId() {
        return frId;
    }

    public void setFrId(int frId) {
        this.frId = frId;
    }

    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(int isClosed) {
        this.isClosed = isClosed;
    }

    public List<SuggestionDetail> getSuggestionDetails() {
        return suggestionDetails;
    }

    public void setSuggestionDetails(List<SuggestionDetail> suggestionDetails) {
        this.suggestionDetails = suggestionDetails;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "SuggestionData{" +
                "suggestionId=" + suggestionId +
                ", title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", frId=" + frId +
                ", frName='" + frName + '\'' +
                ", isClosed=" + isClosed +
                ", suggestionDetails=" + suggestionDetails +
                ", read=" + read +
                '}';
    }
}
