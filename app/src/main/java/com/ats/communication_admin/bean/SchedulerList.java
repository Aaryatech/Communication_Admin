package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 30/1/2018.
 */

public class SchedulerList {

    private int schId;
    private String schDate;
    private String schTodate;
    private String schOccasionname;
    private String schMessage;
    private double schFrdttime;
    private double schTodttime;
    private int isActive;
    private int delStatus;
    private int read;

    public SchedulerList() {
    }

    public SchedulerList(int schId) {
        this.schId = schId;
    }

    public int getSchId() {
        return schId;
    }

    public void setSchId(int schId) {
        this.schId = schId;
    }

    public String getSchDate() {
        return schDate;
    }

    public void setSchDate(String schDate) {
        this.schDate = schDate;
    }

    public String getSchTodate() {
        return schTodate;
    }

    public void setSchTodate(String schTodate) {
        this.schTodate = schTodate;
    }

    public String getSchOccasionname() {
        return schOccasionname;
    }

    public void setSchOccasionname(String schOccasionname) {
        this.schOccasionname = schOccasionname;
    }

    public String getSchMessage() {
        return schMessage;
    }

    public void setSchMessage(String schMessage) {
        this.schMessage = schMessage;
    }

    public double getSchFrdttime() {
        return schFrdttime;
    }

    public void setSchFrdttime(double schFrdttime) {
        this.schFrdttime = schFrdttime;
    }

    public double getSchTodttime() {
        return schTodttime;
    }

    public void setSchTodttime(double schTodttime) {
        this.schTodttime = schTodttime;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "SchedulerList{" +
                "schId=" + schId +
                ", schDate='" + schDate + '\'' +
                ", schTodate='" + schTodate + '\'' +
                ", schOccasionname='" + schOccasionname + '\'' +
                ", schMessage='" + schMessage + '\'' +
                ", schFrdttime=" + schFrdttime +
                ", schTodttime=" + schTodttime +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                ", read=" + read +
                '}';
    }
}
