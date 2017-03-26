package com.weixin.model;

import java.util.List;

/**
 * 被动恢复消息格式类,只用于消息自动回复
 */
public class BeRepliedXMLMessage extends XMLMessage {


    private Integer articleCount; //图文信息的个数，<=8
    private ImageAndVoice imageAndVoice;
    private Video video;
    private Music music;
    private List<ArticlesItem> articlesItemList;


    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public ImageAndVoice getImageAndVoice() {
        return imageAndVoice;
    }

    public void setImageAndVoice(ImageAndVoice imageAndVoice) {
        this.imageAndVoice = imageAndVoice;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public List<ArticlesItem> getArticlesItemList() {
        return articlesItemList;
    }

    public void setArticlesItemList(List<ArticlesItem> articlesItemList) {
        this.articlesItemList = articlesItemList;
    }

    class ImageAndVoice {

        private String mediaId; //通过素材管理中的接口上传多媒体文件，得到的id。

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

    }

    class Video {
        private String mediaId;     //通过素材管理中的接口上传多媒体文件，得到的id
        private String title;       //视频消息的标题
        private String description; //视频消息的描述

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    class Music {
        private String title;           //音乐标题
        private String description;     //音乐描述
        private String musicUrl;        //音乐链接
        private String hqMusicUrl;      //高质量音乐链接，WIFI环境优先使用该链接播放音乐
        private String thumbMediaId;    //缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMusicUrl() {
            return musicUrl;
        }

        public void setMusicUrl(String musicUrl) {
            this.musicUrl = musicUrl;
        }

        public String getHqMusicUrl() {
            return hqMusicUrl;
        }

        public void setHqMusicUrl(String hqMusicUrl) {
            this.hqMusicUrl = hqMusicUrl;
        }

        public String getThumbMediaId() {
            return thumbMediaId;
        }

        public void setThumbMediaId(String thumbMediaId) {
            this.thumbMediaId = thumbMediaId;
        }
    }

    class ArticlesItem {
        private String title;           //图文消息标题
        private String description;     //图文消息描述
        private String picUrl;          //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
        private String url;             //点击图文消息跳转链接

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


}
