package com.weixin.util;

import java.io.Closeable;
import java.io.IOException;

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
                return true;
            }
        }
        return false;
    }

    public static void closeQuietly(Closeable... closeables) throws IOException {
        try {
            if (closeables != null) {
                for (Closeable closeable : closeables) {
                    if (closeable != null) {
                        closeable.close();
                    }
                }
            }
        } catch (IOException e) {
            //@TODO 记录日志 这里需要等待日志框架的上线
            System.out.println("关闭io资源时出错！ " + e);
            throw e;
        }
    }

}
