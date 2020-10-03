package com.digitcreativestudio.ayoolahragaid.network;

import com.digitcreativestudio.ayoolahragaid.model.Rating;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RatingResponse extends Response {
    @Expose(serialize = false)
    @SerializedName("data")
    private ArrayList<Rating> data;

    public ArrayList<Rating> getData() {
        return data;
    }
}
