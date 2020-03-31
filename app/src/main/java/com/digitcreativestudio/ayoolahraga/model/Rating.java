package com.digitcreativestudio.ayoolahraga.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Rating implements Parcelable {
    private String name_user, score, message;

    private Rating(Parcel in) {
        name_user = in.readString();
        score = in.readString();
        message = in.readString();
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name_user);
        dest.writeString(score);
        dest.writeString(message);
    }

    public String getName_user() {
        return name_user;
    }

    public String getScore() {
        return score;
    }

    public String getMessage() {
        return message;
    }
}
