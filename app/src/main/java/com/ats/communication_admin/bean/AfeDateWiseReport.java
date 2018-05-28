package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 19/3/2018.
 */

public class AfeDateWiseReport {

    private int afeScoreHeaderId;
    private int frId;
    private String scoreHeaderDate;
    private int visitedById;
    private String visitPerson;
    private int routeId;
    private int totalScore;
    private int status;
    private String routeName;
    private String frName;
    private String frCode;

    public int getAfeScoreHeaderId() {
        return afeScoreHeaderId;
    }

    public void setAfeScoreHeaderId(int afeScoreHeaderId) {
        this.afeScoreHeaderId = afeScoreHeaderId;
    }

    public int getFrId() {
        return frId;
    }

    public void setFrId(int frId) {
        this.frId = frId;
    }

    public String getScoreHeaderDate() {
        return scoreHeaderDate;
    }

    public void setScoreHeaderDate(String scoreHeaderDate) {
        this.scoreHeaderDate = scoreHeaderDate;
    }

    public int getVisitedById() {
        return visitedById;
    }

    public void setVisitedById(int visitedById) {
        this.visitedById = visitedById;
    }

    public String getVisitPerson() {
        return visitPerson;
    }

    public void setVisitPerson(String visitPerson) {
        this.visitPerson = visitPerson;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }

    public String getFrCode() {
        return frCode;
    }

    public void setFrCode(String frCode) {
        this.frCode = frCode;
    }

    @Override
    public String toString() {
        return "AfeDateWiseReport{" +
                "afeScoreHeaderId=" + afeScoreHeaderId +
                ", frId=" + frId +
                ", scoreHeaderDate='" + scoreHeaderDate + '\'' +
                ", visitedById=" + visitedById +
                ", visitPerson='" + visitPerson + '\'' +
                ", routeId=" + routeId +
                ", totalScore=" + totalScore +
                ", status=" + status +
                ", routeName='" + routeName + '\'' +
                ", frName='" + frName + '\'' +
                ", frCode='" + frCode + '\'' +
                '}';
    }
}
