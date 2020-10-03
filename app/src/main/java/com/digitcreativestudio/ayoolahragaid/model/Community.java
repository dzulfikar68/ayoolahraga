package com.digitcreativestudio.ayoolahragaid.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Community implements Parcelable {

    private int id_community, id_venue;
    private String  name_community,
            description_community,
            photo,
            contact_community,
            medsos_community;
    private List<Operational> operational;
    private ArrayList<Image> image;

    private Community(Parcel in) {
        id_community = in.readInt();
        id_venue = in.readInt();
        name_community = in.readString();
        description_community = in.readString();
        photo = in.readString();
        contact_community = in.readString();
        medsos_community = in.readString();
        operational = in.createTypedArrayList(Operational.CREATOR);
        image = in.createTypedArrayList(Image.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_community);
        dest.writeInt(id_venue);
        dest.writeString(name_community);
        dest.writeString(description_community);
        dest.writeString(photo);
        dest.writeString(contact_community);
        dest.writeString(medsos_community);
        dest.writeTypedList(operational);
        dest.writeTypedList(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Community> CREATOR = new Creator<Community>() {
        @Override
        public Community createFromParcel(Parcel in) {
            return new Community(in);
        }

        @Override
        public Community[] newArray(int size) {
            return new Community[size];
        }
    };

    public int getId_community() {
        return id_community;
    }

    public int getId_venue() {
        return id_venue;
    }

    public String getName_community() {
        return name_community;
    }

    public String getDescription_community() {
        return description_community;
    }

    public String getPhoto() {
        return photo;
    }

    public String getContact_community() {
        return contact_community;
    }

    public String getMedsos_community() {
        return medsos_community;
    }

    public List<Operational> getOperational() {
        return operational;
    }

    public ArrayList<Image> getImage() {
        return image;
    }
}
