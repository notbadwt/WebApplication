package com.weixin.model;

/**
 * 基本消息格式
 */
public class NormalXMLMessage extends XMLMessage {

    private String locationX;   //地理位置纬度
    private String locationY;   //地理位置经度
    private String scale;       //地图缩放大小
    private String label;        //地理位置信息

    private String picUrl;      //图片链接
    private String mediaId;     //图片/语音/视频 消息多媒体id，可以调用多媒体文件下载接口拉取数据
    private String format;      //语音格式
    private String thumbMediaId;//视频消息缩略图媒体id，可以调用多媒体文件下载接口拉取数据

    private String msgId;       //消息id

    private String recognition; //语音识别结果

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (!(obj instanceof NormalXMLMessage)) {
            return false;
        } else {
            NormalXMLMessage xmlMessage = (NormalXMLMessage) obj;
            return this.msgId.equals(xmlMessage.getMsgId());
        }
    }
}
