package com.example.gamingnews.Server.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class InvolvedCompanies implements Parcelable {


    private Company company;
    private boolean developer;

    public InvolvedCompanies(Company company, boolean developer) {
        this.company = company;
        this.developer = developer;
    }

    protected InvolvedCompanies(Parcel in) {
    }

    public static final Creator<InvolvedCompanies> CREATOR = new Creator<InvolvedCompanies>() {
        @Override
        public InvolvedCompanies createFromParcel(Parcel in) {
            return new InvolvedCompanies(in);
        }

        @Override
        public InvolvedCompanies[] newArray(int size) {
            return new InvolvedCompanies[size];
        }
    };

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean getDeveloper() {
        return developer;
    }

    public void setDeveloper(boolean developer) {
        this.developer = developer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public class Company {
        private String name;

        public Company(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
