package com.digitcreativestudio.ayoolahraga.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Venue implements Parcelable {
    private int id_venue;
    private String  name_venue,
                    address_venue,
                    photo,
                    description,
                    rating_total,
                    longitude,
                    latitude,
                    email_user,
                    phone_user;
    private List<String> facility;
    private List<Operational> operational;
    private ArrayList<Image> image;
    private ArrayList<Rating> rating;

    public Venue() {}

    protected Venue(Parcel in) {
        id_venue = in.readInt();
        name_venue = in.readString();
        address_venue = in.readString();
        photo = in.readString();
        description = in.readString();
        rating_total = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        email_user = in.readString();
        phone_user = in.readString();
        facility = in.createStringArrayList();
        operational = in.createTypedArrayList(Operational.CREATOR);
        image = in.createTypedArrayList(Image.CREATOR);
        rating = in.createTypedArrayList(Rating.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_venue);
        dest.writeString(name_venue);
        dest.writeString(address_venue);
        dest.writeString(photo);
        dest.writeString(description);
        dest.writeString(rating_total);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(email_user);
        dest.writeString(phone_user);
        dest.writeStringList(facility);
        dest.writeTypedList(operational);
        dest.writeTypedList(image);
        dest.writeTypedList(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };

    public int getId_venue() {
        return id_venue;
    }

    public String getName_venue() {
        return name_venue;
    }

    public String getAddress_venue() {
        return address_venue;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public String getRating_total() {
        return rating_total;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getEmail_user() {
        return email_user;
    }

    public String getPhone_user() {
        return phone_user;
    }

    public List<String> getFacility() {
        return facility;
    }

    public List<Operational> getOperational() {
        return operational;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public ArrayList<Rating> getRating() {
        return rating;
    }

    public void setId_venue(int id_venue) {
        this.id_venue = id_venue;
    }

    public void setName_venue(String name_venue) {
        this.name_venue = name_venue;
    }

    public void setAddress_venue(String address_venue) {
        this.address_venue = address_venue;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
