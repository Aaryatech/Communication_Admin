package com.ats.communication_admin.bean;

import java.util.List;

/**
 * Created by MAXADMIN on 19/3/2018.
 */

public class AfeHeader {

    private int afeScoreHeaderId;
    private int frId;
    private String scoreHeaderDate;
    private int visitedById;
    private String visitPerson;
    private int routeId;
    private int totalScore;
    private int status;
    private int delStatus;
    private int exInt1;
    private int exInt2;
    private String exVar1;
    private String exVar2;
    private List<AfeDetail> afeDetail;

    public AfeHeader() {
    }

    public AfeHeader(int afeScoreHeaderId, int frId, String scoreHeaderDate, int visitedById, String visitPerson, int routeId, int totalScore, int status, int delStatus, int exInt1, int exInt2, String exVar1, String exVar2, List<AfeDetail> afeDetail) {
        this.afeScoreHeaderId = afeScoreHeaderId;
        this.frId = frId;
        this.scoreHeaderDate = scoreHeaderDate;
        this.visitedById = visitedById;
        this.visitPerson = visitPerson;
        this.routeId = routeId;
        this.totalScore = totalScore;
        this.status = status;
        this.delStatus = delStatus;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.afeDetail = afeDetail;
    }

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

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getExInt1() {
        return exInt1;
    }

    public void setExInt1(int exInt1) {
        this.exInt1 = exInt1;
    }

    public int getExInt2() {
        return exInt2;
    }

    public void setExInt2(int exInt2) {
        this.exInt2 = exInt2;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public String getExVar2() {
        return exVar2;
    }

    public void setExVar2(String exVar2) {
        this.exVar2 = exVar2;
    }

    public List<AfeDetail> getAfeDetail() {
        return afeDetail;
    }

    public void setAfeDetail(List<AfeDetail> afeDetail) {
        this.afeDetail = afeDetail;
    }

    @Override
    public String toString() {
        return "AfeHeader{" +
                "afeScoreHeaderId=" + afeScoreHeaderId +
                ", frId=" + frId +
                ", scoreHeaderDate='" + scoreHeaderDate + '\'' +
                ", visitedById=" + visitedById +
                ", visitPerson='" + visitPerson + '\'' +
                ", routeId=" + routeId +
                ", totalScore=" + totalScore +
                ", status=" + status +
                ", delStatus=" + delStatus +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", afeDetail=" + afeDetail +
                '}';
    }
}


