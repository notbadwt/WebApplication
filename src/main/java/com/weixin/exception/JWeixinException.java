package com.weixin.exception;

/**
 * Created by Administrator on 2017/2/22 0022.
 */
public class JWeixinException extends Exception {

    public static final Integer EXCEPTION_PARAM_CHECK = 3050001;
    public static final Integer EXCEPTION_SYSTEM = 3050002;


    private Integer errcode;
    private String errmsg;


    public JWeixinException(String message) {
        super(message);
        errcode = EXCEPTION_SYSTEM;
    }

    public JWeixinException() {
    }

    public JWeixinException(String message, Throwable cause) {
        super(message, cause);
        errcode = EXCEPTION_SYSTEM;
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

    protected void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }


}
