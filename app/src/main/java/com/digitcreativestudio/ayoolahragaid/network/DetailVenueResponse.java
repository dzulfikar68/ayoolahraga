package com.digitcreativestudio.ayoolahragaid.network;

import com.digitcreativestudio.ayoolahragaid.model.Venue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailVenueResponse extends Response {
    @Expose(serialize = false)
    @SerializedName("data")
    private Venue data;

    public Venue getData() {
        return data;
    }
}
