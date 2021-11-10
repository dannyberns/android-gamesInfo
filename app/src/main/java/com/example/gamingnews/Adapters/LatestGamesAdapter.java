package com.example.gamingnews.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamingnews.R;
import com.example.gamingnews.Server.Models.Game;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LatestGamesAdapter extends RecyclerView.Adapter<LatestGamesAdapter.GameViewHolder>{

    private final List<Game> gamesList;
    private final Context mContext;

    public LatestGamesAdapter(List<Game> gamesList, Context mContext) {
        this.gamesList = gamesList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_recent_game, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        holder.name.setText(gamesList.get(position).getName());
        holder.date.setText(gamesList.get(position).getRelease_dates().get(0).getHuman());

        Picasso.get()
                .load(gamesList.get(position).getImage())
                .into(holder.img);


        //passing variables to gameInfo fragment
        Game gGame = gamesList.get(position);


        Bundle bundle = new Bundle();
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();

                bundle.putParcelable("game", gGame);

                NavController navController = Navigation.findNavController(activity, R.id.fragmentContainerView);
                navController.navigate(R.id.action_fragmentMainScreen_to_fragmentGameInfo, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder{

        private final TextView name, date;
        private final ImageView img;
        private final CardView cardView;

        public GameViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.viewpager_img);
            name = view.findViewById(R.id.viewpager_name);
            date = view.findViewById(R.id.viewpager_date);
            cardView = view.findViewById(R.id.viewpager_cardview);
        }

    }

}
