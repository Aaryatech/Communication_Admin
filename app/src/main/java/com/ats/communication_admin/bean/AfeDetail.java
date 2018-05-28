package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 19/3/2018.
 */

public class AfeDetail {

    private int afeScoreDetailId;
    private int afeScoreHeaderId;
    private int queId;
    private int quePoint;
    private int score;
    private String remark;
    private int delStatus;
    private int exInt1;
    private String exVar1;

    public AfeDetail() {
    }

    public AfeDetail(int afeScoreDetailId, int afeScoreHeaderId, int queId, int quePoint, int score, String remark, int delStatus, int exInt1, String exVar1) {
        this.afeScoreDetailId = afeScoreDetailId;
        this.afeScoreHeaderId = afeScoreHeaderId;
        this.queId = queId;
        this.quePoint = quePoint;
        this.score = score;
        this.remark = remark;
        this.delStatus = delStatus;
        this.exInt1 = exInt1;
        this.exVar1 = exVar1;
    }

    public int getAfeScoreDetailId() {
        return afeScoreDetailId;
    }

    public void setAfeScoreDetailId(int afeScoreDetailId) {
        this.afeScoreDetailId = afeScoreDetailId;
    }

    public int getAfeScoreHeaderId() {
        return afeScoreHeaderId;
    }

    public void setAfeScoreHeaderId(int afeScoreHeaderId) {
        this.afeScoreHeaderId = afeScoreHeaderId;
    }

    public int getQueId() {
        return queId;
    }

    public void setQueId(int queId) {
        this.queId = queId;
    }

    public int getQuePoint() {
        return quePoint;
    }

    public void setQuePoint(int quePoint) {
        this.quePoint = quePoint;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }


    @Override
    public String toString() {
        return "AfeDetail{" +
                "afeScoreDetailId=" + afeScoreDetailId +
                ", afeScoreHeaderId=" + afeScoreHeaderId +
                ", queId=" + queId +
                ", quePoint=" + quePoint +
                ", score=" + score +
                ", remark='" + remark + '\'' +
                ", delStatus=" + delStatus +
                ", exInt1=" + exInt1 +
                ", exVar1='" + exVar1 + '\'' +
                '}';
    }
}
