package com.challenge.hufsy.mystories.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/3/18.
 * <p>
 */
public class Story implements Parcelable{

    private String name;
    private String downloadUrl;
    private long date;

    public Story() {

    }

    public Story(String name, String downloadUrl, long date) {
        this.name = name;
        this.downloadUrl = downloadUrl;
        this.date = date;
    }

    protected Story(Parcel in) {
        name = in.readString();
        downloadUrl = in.readString();
        date = in.readLong();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(downloadUrl);
        dest.writeLong(date);
    }
}
