package com.example.gamingnews.Server.Services;


import com.example.gamingnews.Server.Models.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @Headers({
            "Client-ID: 32rlwpyvm20j6kuugz9v3v6107319n",
            "Authorization: Bearer co4xcskolmluwy8irmjljd7uaf9srv",
            "Content-Type: text/plain"

    })
    @POST("games")
    Call<List<Game>> getGames(@Query("fields") String fields);

}
