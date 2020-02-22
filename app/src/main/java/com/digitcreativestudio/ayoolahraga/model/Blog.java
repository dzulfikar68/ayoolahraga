package com.digitcreativestudio.ayoolahraga.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Blog implements Parcelable {
    private int id_blog;
    private String title;
    private String image;
    private String link;

    public Blog() {}

    protected Blog(Parcel in) {
        id_blog = in.readInt();
        title = in.readString();
        image = in.readString();
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_blog);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Blog> CREATOR = new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel in) {
            return new Blog(in);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };

    public int getId() {
        return id_blog;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public void setId_blog(int id_blog) {
        this.id_blog = id_blog;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
