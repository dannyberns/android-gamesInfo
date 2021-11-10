package com.example.gamingnews.Server.Models;

import android.os.Parcel;

public class Collection {

    private String name;

    protected Collection(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
