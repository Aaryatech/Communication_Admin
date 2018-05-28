package com.ats.communication_admin.bean;

/**
 * Created by MAXADMIN on 3/2/2018.
 */

public class AdminData {

    private User user;
    private ErrorMessage errorMessage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "AdminData{" +
                "user=" + user +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
