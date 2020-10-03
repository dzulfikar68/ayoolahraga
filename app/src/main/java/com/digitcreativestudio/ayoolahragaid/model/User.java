package com.digitcreativestudio.ayoolahragaid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int id_user;
    private String name_user, address_user, phone, email, birth, hoby;

    private User(Parcel in) {
        id_user = in.readInt();
        name_user = in.readString();
        address_user = in.readString();
        phone = in.readString();
        email = in.readString();
        birth = in.readString();
        hoby = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_user);
        dest.writeString(name_user);
        dest.writeString(address_user);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(birth);
        dest.writeString(hoby);
    }

    public int getId() {
        return id_user;
    }

    public String getName_user() {
        return name_user;
    }

    public String getAddress_user() {
        return address_user;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBirth() {
        return birth;
    }

    public String getHoby() {
        return hoby;
    }
}
