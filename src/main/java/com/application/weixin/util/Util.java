package com.application.weixin.util;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class Util {

    /**
     * 所有的参数都不能为null如果有其中一个为null就反对false
     *
     * @param params 参数列表
     * @return true表示所欲参数不为null
     */
    public static Boolean paramNullValueCheck(Object... params) {
        for (Object param : params) {
            if (param == null) {
                return false;
            }
        }
        return true;
    }

}
