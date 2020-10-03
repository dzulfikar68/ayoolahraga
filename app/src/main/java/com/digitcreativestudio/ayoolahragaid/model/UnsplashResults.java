package com.digitcreativestudio.ayoolahragaid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UnsplashResults implements Parcelable {
    private UnsplashUrls urls;

    private UnsplashResults(Parcel in) {
        urls = in.readParcelable(UnsplashUrls.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(urls, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UnsplashResults> CREATOR = new Creator<UnsplashResults>() {
        @Override
        public UnsplashResults createFromParcel(Parcel in) {
            return new UnsplashResults(in);
        }

        @Override
        public UnsplashResults[] newArray(int size) {
            return new UnsplashResults[size];
        }
    };

    public UnsplashUrls getUrls() {
        return urls;
    }
}
