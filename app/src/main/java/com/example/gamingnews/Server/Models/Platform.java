package com.example.gamingnews.Server.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Platform implements Parcelable {

    private String name;

    protected Platform(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Platform> CREATOR = new Creator<Platform>() {
        @Override
        public Platform createFromParcel(Parcel in) {
            return new Platform(in);
        }

        @Override
        public Platform[] newArray(int size) {
            return new Platform[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
