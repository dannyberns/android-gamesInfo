package com.example.gamingnews.Server.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Expansion implements Parcelable {

    private String name;

    protected Expansion(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Expansion> CREATOR = new Creator<Expansion>() {
        @Override
        public Expansion createFromParcel(Parcel in) {
            return new Expansion(in);
        }

        @Override
        public Expansion[] newArray(int size) {
            return new Expansion[size];
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
