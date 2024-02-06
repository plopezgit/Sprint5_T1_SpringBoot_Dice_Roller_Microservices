package com.plopez.diceroller.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.plopez.diceroller.app.adapters.GameAdapter;
import com.plopez.diceroller.app.interfaces.RollerDiceInterface;
import com.plopez.diceroller.app.model.Game;
import com.plopez.diceroller.app.utils.Constants;

import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Game> games;
    RollerDiceInterface rollerDiceInterface;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        getAll();
    }

    private void getAll() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.FINAL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        rollerDiceInterface = retrofit.create(RollerDiceInterface.class);
        Call<List<Game>> call = rollerDiceInterface.getAll();
        call.enqueue(new Callback<List<Game>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if(!response.isSuccessful()) {
                    Log.e("Response error: ", response.message());
                    return;
                }
                games = response.body();
                GameAdapter gameAdapter = new GameAdapter(games, getApplicationContext());
                listView.setAdapter(gameAdapter);
                games.forEach(game -> Log.i("Info: ", game.toString()));
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e("Throw error: ", t.getMessage());
            }
        });
    }
}