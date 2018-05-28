package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 19/2/2018.
 */

public class FranchiseByRoute {

    private int frId;
    private String frName;
    private String frCode;

    public FranchiseByRoute(int frId, String frName, String frCode) {
        this.frId = frId;
        this.frName = frName;
        this.frCode = frCode;
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

    public String getFrCode() {
        return frCode;
    }

    public void setFrCode(String frCode) {
        this.frCode = frCode;
    }

    @Override
    public String toString() {
        return "FranchiseByRoute{" +
                "frId=" + frId +
                ", frName='" + frName + '\'' +
                ", frCode='" + frCode + '\'' +
                '}';
    }
}
