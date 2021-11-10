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

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GamelViewHolder> {

    private Context mContext;
    private List<Game> gamesList;
    private String location;


    public GameListAdapter(Context mContext, List<Game> gamesList, String location) {
        this.mContext = mContext;
        this.gamesList = gamesList;
        this.location = location;
    }

    @NonNull
    @Override
    public GamelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        v = layoutInflater.inflate(R.layout.item_cardview_game, parent, false);
        return new GamelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GamelViewHolder holder, int position) {

        //holder
        holder.name.setText(gamesList.get(position).getName());
        // adding picasso library to handle images
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

                if(location.equals("search")){
                    navController.navigate(R.id.action_fragmentSearch2_to_fragmentGameInfo, bundle);

                } else {
                    navController.navigate(R.id.action_fragmentGameList_to_fragmentGameInfo, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }


    public static class GamelViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView img;
        CardView cardView;

        public GamelViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.game_name_id);
            img = itemView.findViewById(R.id.game_img_id);
            cardView = itemView.findViewById(R.id.cardView_id1);
        }
    }
}
