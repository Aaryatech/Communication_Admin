package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 1/2/2018.
 */

public class ComplaintData {

    private int complaintId;
    private String title;
    private String description;
    private String photo1;
    private String photo2;
    private int frId;
    private String frName;
    private String customerName;
    private String mobileNumber;
    private String date;
    private String time;
    private int isClosed;
    private int read;

    public ComplaintData() {
    }

    public ComplaintData(int complaintId, String title, String description, String photo1, int frId, String customerName, String mobileNumber, int isClosed) {
        this.complaintId = complaintId;
        this.title = title;
        this.description = description;
        this.photo1 = photo1;
        this.frId = frId;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.isClosed = isClosed;
    }

    public ComplaintData(int complaintId, String title, String description, String photo1, String photo2, int frId, String frName, String customerName, String mobileNumber, String date, String time, int isClosed, int read) {
        this.complaintId = complaintId;
        this.title = title;
        this.description = description;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.frId = frId;
        this.frName = frName;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.date = date;
        this.time = time;
        this.isClosed = isClosed;
        this.read = read;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
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

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
        return "ComplaintData{" +
                "complaintId=" + complaintId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photo1='" + photo1 + '\'' +
                ", photo2='" + photo2 + '\'' +
                ", frId=" + frId +
                ", frName='" + frName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isClosed=" + isClosed +
                ", read=" + read +
                '}';
    }
}
