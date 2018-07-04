package com.challenge.hufsy.mystories.model;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/4/18.
 * <p>
 */
public class UploadStoryInfo {

    private String name;
    private String downloadUrl;
    private long date;

    public UploadStoryInfo(String name, String downloadUrl, long date) {
        this.name = name;
        this.downloadUrl = downloadUrl;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
