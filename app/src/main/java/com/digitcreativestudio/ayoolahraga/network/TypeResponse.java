package com.digitcreativestudio.ayoolahraga.network;

import com.digitcreativestudio.ayoolahraga.model.Type;
import com.digitcreativestudio.ayoolahraga.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TypeResponse extends Response {

    @Expose(serialize = false)
    @SerializedName("data")
    private ArrayList<Type> data;

    public ArrayList<Type> getData() {
        return data;
    }

}
