package com.digitcreativestudio.ayoolahraga.network;

import com.digitcreativestudio.ayoolahraga.model.UnsplashResults;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

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
