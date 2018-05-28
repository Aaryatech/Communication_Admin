package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 29/1/2018.
 */

public class Info {

    private String message;
    private Boolean error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Info{" +
                "message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}
