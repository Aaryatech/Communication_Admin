package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 1/2/2018.
 */

public class FeedbackData {

    private int feedbackId;
    private String title;
    private int userId;
    private String userName;
    private String photo;
    private String description;
    private String date;
    private String time;
    private int isClosed;
    private int read;

    public FeedbackData() {
    }

    public FeedbackData(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public FeedbackData(int feedbackId, String title, int userId, String userName, String photo, String description, String date, String time, int isClosed, int read) {
        this.feedbackId = feedbackId;
        this.title = title;
        this.userId = userId;
        this.userName = userName;
        this.photo = photo;
        this.description = description;
        this.date = date;
        this.time = time;
        this.isClosed = isClosed;
        this.read = read;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(int isClosed) {
        this.isClosed = isClosed;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "FeedbackData{" +
                "feedbackId=" + feedbackId +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isClosed=" + isClosed +
                ", read=" + read +
                '}';
    }
}
