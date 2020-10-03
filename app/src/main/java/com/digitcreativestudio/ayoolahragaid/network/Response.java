package com.digitcreativestudio.ayoolahragaid.network;

import com.google.gson.annotations.Expose;

public class Response {

    @Expose(serialize = false)
    private String status;
    @Expose(serialize = false)
    private String message;

    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }

}
