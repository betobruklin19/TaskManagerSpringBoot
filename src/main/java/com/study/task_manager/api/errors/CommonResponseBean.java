package com.study.task_manager.api.errors;

import java.util.List;

public class CommonResponseBean {


    private List<ErrorBean> errors;

    // Getters and setters

    public List<ErrorBean> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorBean> errors) {
        this.errors = errors;
    }
}