package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 29/1/2018.
 */

public class LoginData {

    private LoginInfo loginInfo;
    private Franchisee franchisee;

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public Franchisee getFranchisee() {
        return franchisee;
    }

    public void setFranchisee(Franchisee franchisee) {
        this.franchisee = franchisee;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "loginInfo=" + loginInfo +
                ", franchisee=" + franchisee +
                '}';
    }
}
