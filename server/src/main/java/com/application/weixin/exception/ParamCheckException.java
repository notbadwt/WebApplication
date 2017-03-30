package com.application.weixin.exception;

public class ParamCheckException extends JWeixinException {


    public ParamCheckException(String message) {
        super(message);
        setErrcode(EXCEPTION_PARAM_CHECK);
        setErrmsg(message);
    }

    public ParamCheckException() {
        super();
        setErrcode(EXCEPTION_PARAM_CHECK);
        setErrmsg("参数校验失败");
    }
}
