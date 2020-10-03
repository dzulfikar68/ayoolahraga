package com.digitcreativestudio.ayoolahragaid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Operational implements Parcelable {
    private String day;
    private String hour;

    private Operational(Parcel in) {
        day = in.readString();
        hour = in.readString();
    }

    public static final Creator<Operational> CREATOR = new Creator<Operational>() {
        @Override
        public Operational createFromParcel(Parcel in) {
            return new Operational(in);
        }

        @Override
        public Operational[] newArray(int size) {
            return new Operational[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeString(hour);
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }
}