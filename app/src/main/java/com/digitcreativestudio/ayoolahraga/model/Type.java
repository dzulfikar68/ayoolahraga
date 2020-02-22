package com.digitcreativestudio.ayoolahraga.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Type implements Parcelable {
    private int id_type;
    private String name_type;

    public Type() {}

    protected Type(Parcel in) {
        id_type = in.readInt();
        name_type = in.readString();
    }

    public static final Creator<Type> CREATOR = new Creator<Type>() {
        @Override
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        @Override
        public Type[] newArray(int size) {
            return new Type[size];
        }
    };

    public int getId() {
        return id_type;
    }

    public String getTitle() {
        return name_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_type);
        dest.writeString(name_type);
    }
}
