package com.example.gamingnews.Server.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static Api instance = null;
    public static final String BASE_URL = "https://api.igdb.com/v4/";

    private UserService userService;

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }

        return instance;
    }

    private Api() {
        buildRetrofit();
    }

    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Build services once
        this.userService = retrofit.create(UserService.class);

    }

    public UserService getUserService() {
        return this.userService;
    }
}
