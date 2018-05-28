package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 30/1/2018.
 */

public class NotificationData {

    private int notificationId;

    private String subject;
    private int userId;
    private String userName;
    private String description;
    private String photo;
    private String date;
    private String time;
    private int isClosed;
    private int read;

    public NotificationData() {
    }

    public NotificationData(int notificationId, String subject, int userId, String userName, String description, String photo, String date, String time, int isClosed, int read) {
        this.notificationId = notificationId;
        this.subject = subject;
        this.userId = userId;
        this.userName = userName;
        this.description = description;
        this.photo = photo;
        this.date = date;
        this.time = time;
        this.isClosed = isClosed;
        this.read = read;
    }

    public NotificationData(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "NotificationData{" +
                "notificationId=" + notificationId +
                ", subject='" + subject + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isClosed=" + isClosed +
                ", read=" + read +
                '}';
    }
}
