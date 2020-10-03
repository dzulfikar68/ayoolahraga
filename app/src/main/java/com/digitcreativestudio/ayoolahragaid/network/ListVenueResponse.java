package com.digitcreativestudio.ayoolahragaid.network;

import com.digitcreativestudio.ayoolahragaid.model.Venue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListVenueResponse extends Response {
    @Expose(serialize = false)
    @SerializedName("data")
    private ArrayList<Venue> data;

    public ArrayList<Venue> getData() {
        return data;
    }
}
