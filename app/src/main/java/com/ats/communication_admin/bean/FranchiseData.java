package com.ats.communication_admin.bean;

import java.util.List;

/**
 * Created by MAXADMIN on 19/3/2018.
 */

public class FranchiseData {

    private List<FranchiseeList> franchiseeList;
    private ErrorMessage errorMessage;

    public List<FranchiseeList> getFranchiseeList() {
        return franchiseeList;
    }

    public void setFranchiseeList(List<FranchiseeList> franchiseeList) {
        this.franchiseeList = franchiseeList;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "FranchiseData{" +
                "franchiseeList=" + franchiseeList +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
