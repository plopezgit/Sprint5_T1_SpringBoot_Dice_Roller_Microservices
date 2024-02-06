package com.plopez.diceroller.app.adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plopez.diceroller.app.R;
import com.plopez.diceroller.app.model.Game;

import java.util.List;

public class GameAdapter extends BaseAdapter {

    List<Game> games;
    Context context;
    TextView playerId;
    TextView die1;
    TextView die2;
    TextView result;
    ImageView dice;


    public GameAdapter(List<Game> games, Context context) {
        this.games = games;
        this.context = context;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int i) {
        return games.get(i).getId();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.game_list, viewGroup, false);

        }
        playerId = view.findViewById(R.id.playerId);
        playerId.setText(Integer.toString(games.get(position).getPlayerId()));
        die1 = view.findViewById(R.id.die1);
        die1.setText(Integer.toString(games.get(position).getDie1()));
        die2 = view.findViewById(R.id.die2);
        die2.setText(Integer.toString(games.get(position).getDie2()));
        result = view.findViewById(R.id.result);
        result.setText(Integer.toString(games.get(position).getResult()));

        return view;
    }
}
