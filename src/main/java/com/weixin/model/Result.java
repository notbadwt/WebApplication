package com.weixin.model;

/**
 * 结果容器，Weixin类型对象所有对外的业务接口返回都所使用Result来包装
 * @param <T> 结果值得类型参数
 */
public class Result<T> {

    public static final String SUCCESS = "0";
    public static final String ERROR = "1";

    private String resultStatus;
    private Integer errcode;
    private String errmsg;

    private T value;

    public Result(T value) {
        this.value = value;
        resultStatus = SUCCESS;
    }

    public Result(Integer errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        resultStatus = ERROR;
    }

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

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
