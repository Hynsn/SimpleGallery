package com.hynson.gallery.entity;

public class ImageBean{
    private long id;
    private int serial;
    private String tags;
    private String previewUrl;
    private int likes;
    private int previewWidth;
    private int previewHeight;
    private String webformatURL;
    private int webformatHeight;
    private int webformatWidth;

    public ImageBean(long id, String tags, int likes, String previewUrl,int previewWidth, int previewHeight, String webformatURL, int webformatHeight, int webformatWidth) {
        this.id = id;
        this.tags = tags;
        this.previewUrl = previewUrl;
        this.likes = likes;
        this.previewWidth = previewWidth;
        this.previewHeight = previewHeight;
        this.webformatURL = webformatURL;
        this.webformatHeight = webformatHeight;
        this.webformatWidth = webformatWidth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public int getWebformatHeight() {
        return webformatHeight;
    }

    public void setWebformatHeight(int webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    public int getWebformatWidth() {
        return webformatWidth;
    }

    public void setWebformatWidth(int webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "ImageBean("+id+","+tags+","+likes+","+previewUrl+","+previewWidth+","+previewHeight+","+webformatURL+","+webformatWidth+","+webformatHeight+")";
    }
}
