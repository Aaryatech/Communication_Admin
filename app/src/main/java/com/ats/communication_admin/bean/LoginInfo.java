package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 29/1/2018.
 */

public class LoginInfo {

    private Boolean error;
    private String message;
    private Integer accessRight;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(Integer accessRight) {
        this.accessRight = accessRight;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", accessRight=" + accessRight +
                '}';
    }
}
