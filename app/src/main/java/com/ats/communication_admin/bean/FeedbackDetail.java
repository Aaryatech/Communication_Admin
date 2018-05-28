package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 1/2/2018.
 */

public class FeedbackDetail {

    private int feedbackDetailId;
    private int feedbackId;
    private String message;
    private int isAdmin;
    private int frId;
    private String frName;
    private String photo;
    private String date;
    private String time;
    private int read;

    public FeedbackDetail() {
    }

    public FeedbackDetail(int feedbackDetailId, int feedbackId, String message, int isAdmin, int frId, String frName, String photo, String date, String time, int read) {
        this.feedbackDetailId = feedbackDetailId;
        this.feedbackId = feedbackId;
        this.message = message;
        this.isAdmin = isAdmin;
        this.frId = frId;
        this.frName = frName;
        this.photo = photo;
        this.date = date;
        this.time = time;
        this.read = read;
    }

    public int getFeedbackDetailId() {
        return feedbackDetailId;
    }

    public void setFeedbackDetailId(int feedbackDetailId) {
        this.feedbackDetailId = feedbackDetailId;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "FeedbackDetail{" +
                "feedbackDetailId=" + feedbackDetailId +
                ", feedbackId=" + feedbackId +
                ", message='" + message + '\'' +
                ", isAdmin=" + isAdmin +
                ", frId=" + frId +
                ", frName='" + frName + '\'' +
                ", photo='" + photo + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", read=" + read +
                '}';
    }
}

