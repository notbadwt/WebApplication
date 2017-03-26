package com.weixin.model;

/**
 * 事件消息格式
 */
public class EventXMLMessage extends XMLMessage {

    private String event;       //事件类型 订阅（subscribe） 取消订阅（unsubscribe） 已关注时时间推送（SCAN） 上报地理位置（LOCATION）点击拉取信息（CLICK） 点击跳转链接（VIEW）
    private String eventKey;    //事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
    private String ticket;      //二维码的ticket 可以用来换取二维码图片
    private String latitude;    //地理位置纬度
    private String longitude;   //地理位置经度
    private String precision;   //地理位置精度

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }
}
