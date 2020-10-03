package com.digitcreativestudio.ayoolahragaid.network;

import com.digitcreativestudio.ayoolahragaid.model.Type;
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
