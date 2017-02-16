package com.application.weixin.model;


public abstract class Result {

    public static final String SUCCESS = "0";
    public static final String ERROR = "1";

    private String resultStatus;
    private Integer errcode;
    private String errmsg;

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
