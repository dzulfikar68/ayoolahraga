package com.digitcreativestudio.ayoolahragaid.model;

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
            phone_user,
            telp_venue,
            handphone_venue,
            link_facebook,
            link_instagram,
            city_text,
            provinsi_text;
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
        telp_venue = in.readString();
        handphone_venue = in.readString();
        link_facebook = in.readString();
        link_instagram = in.readString();
        city_text = in.readString();
        provinsi_text = in.readString();
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
        dest.writeString(telp_venue);
        dest.writeString(handphone_venue);
        dest.writeString(link_facebook);
        dest.writeString(link_instagram);
        dest.writeString(city_text);
        dest.writeString(provinsi_text);
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

    //==============================================================

    public String getTelp_venue() {
        return telp_venue;
    }

    public void setTelp_venue(String telp_venue) {
        this.telp_venue = telp_venue;
    }

    public String getHandphone_venue() {
        return handphone_venue;
    }

    public void setHandphone_venue(String handphone_venue) {
        this.handphone_venue = handphone_venue;
    }

    public String getLink_facebook() {
        return link_facebook;
    }

    public void setLink_facebook(String link_facebook) {
        this.link_facebook = link_facebook;
    }

    public String getLink_instagram() {
        return link_instagram;
    }

    public void setLink_instagram(String link_instagram) {
        this.link_instagram = link_instagram;
    }

    public String getCity_text() {
        return city_text;
    }

    public void setCity_text(String city_text) {
        this.city_text = city_text;
    }

    public String getProvinsi_text() {
        return provinsi_text;
    }

    public void setProvinsi_text(String provinsi_text) {
        this.provinsi_text = provinsi_text;
    }
}
