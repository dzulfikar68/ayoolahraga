package com.digitcreativestudio.ayoolahragaid.network;

import com.digitcreativestudio.ayoolahragaid.model.Community;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailCommunityResponse extends Response {
    @Expose(serialize = false)
    @SerializedName("data")
    private Community data;

    public Community getData() {
        return data;
    }
}
