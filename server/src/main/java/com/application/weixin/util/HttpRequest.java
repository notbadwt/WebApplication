package com.application.weixin.util;

import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import static com.application.weixin.util.Util.closeQuietly;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static JsonReader sendGet(String url, String param) throws IOException {
        String urlNameString = httpRequestParamCheck(url, param);
        BufferedReader in = null;
        try {
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            return new JsonReader(new InputStreamReader(
                    connection.getInputStream()));
        } catch (IOException e) {
            //@TODO 记录日志
            System.out.println("发送GET请求出现异常！" + e);
            throw e;
        }
    }


    /**
     * 向指定 URL 发送POST方法的请求 @TODO 还未进行测试
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static JsonReader sendPost(String url, String param) throws IOException {
        PrintWriter out = null;
        url = httpRequestParamCheck(url, "");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            if (param != null) {
                out.print(param);
            }
            // flush输出流的缓冲
            out.flush();
            return new JsonReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            //@TODO 记录日志
            System.out.println("发送 POST 请求出现异常！" + e);
            throw e;
        } finally {
            closeQuietly(out);
        }
    }


    private static String httpRequestParamCheck(String url, String param) {
        StringBuilder urlResult = new StringBuilder();
        if (url != null && !"".equals(url.trim())) {
            urlResult.append(url);
        }
        if (param != null && !"".equals(param.trim())) {
            urlResult.append("?");
            urlResult.append(param);
        }
        return urlResult.toString();
    }
}
