package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 1/2/2018.
 */

public class ComplaintDetail {

    private int compDetailId;
    private int complaintId;
    private String message;
    private String photo;
    private String date;
    private String time;
    private int isAdmin;
    private int frId;
    private String frName;
    private int read;

    public ComplaintDetail() {
    }

    public ComplaintDetail(int compDetailId, int complaintId, String message, String photo, String date, String time, int isAdmin, int frId, String frName, int read) {
        this.compDetailId = compDetailId;
        this.complaintId = complaintId;
        this.message = message;
        this.photo = photo;
        this.date = date;
        this.time = time;
        this.isAdmin = isAdmin;
        this.frId = frId;
        this.frName = frName;
        this.read = read;
    }

    public int getCompDetailId() {
        return compDetailId;
    }

    public void setCompDetailId(int compDetailId) {
        this.compDetailId = compDetailId;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "ComplaintDetail{" +
                "compDetailId=" + compDetailId +
                ", complaintId=" + complaintId +
                ", message='" + message + '\'' +
                ", photo='" + photo + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isAdmin=" + isAdmin +
                ", frId=" + frId +
                ", frName='" + frName + '\'' +
                ", read=" + read +
                '}';
    }
}
