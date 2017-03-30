package com.application.weixin.model;

/**
 * Created by Administrator on 2017/2/24 0024.
 */
public class XMLMessage {


    public static final String TYPE_TEXT = "text";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_VOICE = "voice";
    public static final String TYPE_SHORTVIDEO = "shortvideo";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_LOCATION = "location";
    public static final String TYPE_EVENT = "event";

    //通用字段（基类）
    private String toUserName;  //开发者微信号
    private String fromUserName;//发送方账号（openId）
    private Long createTime;    //消息创建时间
    private String msgType;     //文本消息：text 图片消息：image 语音消息：voice 视频消息：video 小视频消息：shortvideo 地理位置：location,事件消息：event

    private String content;     //文本消息内容


    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
