package com.application.weixin.model;

/**
 * Created by Administrator on 2017/2/17 0017.
 */
public class StringResult extends Result {

    private String value;

    public StringResult(String value) {
        this.value = value;
        this.setResultStatus(Result.SUCCESS);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
