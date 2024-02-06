package com.plopez.diceroller.app.interfaces;

import com.plopez.diceroller.app.model.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RollerDiceInterface {

    @GET("/games")
    Call<List< Game>> getAll();

}
