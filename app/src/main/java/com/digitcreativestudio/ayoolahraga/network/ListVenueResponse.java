package com.digitcreativestudio.ayoolahraga.network;

import com.digitcreativestudio.ayoolahraga.model.Type;
import com.digitcreativestudio.ayoolahraga.model.Venue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListVenueResponse extends Response {
    @Expose(serialize = false)
    @SerializedName("data")
    private ArrayList<Venue> data;

    public ArrayList<Venue> getData() {
        return data;
    }
}
