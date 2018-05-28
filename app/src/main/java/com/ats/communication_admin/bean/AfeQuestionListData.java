package com.ats.communication_admin.bean;

import java.util.List;

/**
 * Created by MAXADMIN on 19/3/2018.
 */

public class AfeQuestionListData {

    private List<AfeQuestion> afeQuestion;
    private Info info;

    public List<AfeQuestion> getAfeQuestion() {
        return afeQuestion;
    }

    public void setAfeQuestion(List<AfeQuestion> afeQuestion) {
        this.afeQuestion = afeQuestion;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "AfeQuestionListData{" +
                "afeQuestion=" + afeQuestion +
                ", info=" + info +
                '}';
    }
}
