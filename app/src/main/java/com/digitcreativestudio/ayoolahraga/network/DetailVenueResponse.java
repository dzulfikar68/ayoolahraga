package com.digitcreativestudio.ayoolahraga.network;

import com.digitcreativestudio.ayoolahraga.model.Type;
import com.digitcreativestudio.ayoolahraga.model.Venue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailVenueResponse extends Response {
    @Expose(serialize = false)
    @SerializedName("data")
    private Venue data;

    public Venue getData() {
        return data;
    }
}
