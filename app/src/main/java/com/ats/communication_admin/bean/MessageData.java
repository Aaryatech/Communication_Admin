package com.ats.communication_admin.bean;

import java.util.List;

/**
 * Created by MAXADMIN on 29/1/2018.
 */

public class MessageData {

    private List<Message> message;
    private Info info;

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "message=" + message +
                ", info=" + info +
                '}';
    }
}
