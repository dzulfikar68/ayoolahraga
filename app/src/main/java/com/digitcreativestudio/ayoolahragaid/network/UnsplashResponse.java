package com.digitcreativestudio.ayoolahragaid.network;

import com.digitcreativestudio.ayoolahragaid.model.UnsplashResults;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class UnsplashResponse {
    @Expose(serialize = false)
    private String total;
    @Expose(serialize = false)
    private String total_pages;
    @Expose(serialize = false)
    private ArrayList<UnsplashResults> results;

    public ArrayList<UnsplashResults> getResults() {
        return results;
    }
}
