package com.digitcreativestudio.ayoolahraga.network;

import com.digitcreativestudio.ayoolahraga.model.Community;
import com.digitcreativestudio.ayoolahraga.model.Type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListCommunityResponse {

    @Expose(serialize = false)
    @SerializedName("data")
    private ArrayList<Community> data;

    public ArrayList<Community> getData() {
        return data;
    }

}
