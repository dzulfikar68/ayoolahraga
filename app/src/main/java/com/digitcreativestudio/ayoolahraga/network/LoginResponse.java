package com.digitcreativestudio.ayoolahraga.network;

import com.digitcreativestudio.ayoolahraga.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends Response {

    @Expose(serialize = false)
    @SerializedName("data")
    private User data;

    public User getData() {
        return data;
    }

}
