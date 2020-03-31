package com.digitcreativestudio.ayoolahraga.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UnsplashUrls implements Parcelable {
    private String thumb;

    private UnsplashUrls(Parcel in) {
        thumb = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumb);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UnsplashUrls> CREATOR = new Creator<UnsplashUrls>() {
        @Override
        public UnsplashUrls createFromParcel(Parcel in) {
            return new UnsplashUrls(in);
        }

        @Override
        public UnsplashUrls[] newArray(int size) {
            return new UnsplashUrls[size];
        }
    };

    public String getThumb() {
        return thumb;
    }
}
