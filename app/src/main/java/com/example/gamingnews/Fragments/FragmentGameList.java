package com.example.gamingnews.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gamingnews.MainActivity;
import com.example.gamingnews.R;
import com.example.gamingnews.databinding.FragmentGameListBinding;
import com.example.gamingnews.Adapters.GameListAdapter;
import com.example.gamingnews.Server.Services.Api;
import com.example.gamingnews.Server.Models.Game;
import com.example.gamingnews.Server.Services.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGameList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGameList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // my variables
    private RecyclerView recyclerView;
    private GameListAdapter adapter;
    private Context mContext;
    private List<Game> gameList;
    private ProgressBar progressBar;
    private TextView loading;
    private View fView;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    public FragmentGameList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGameList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGameList newInstance(String param1, String param2) {
        FragmentGameList fragment = new FragmentGameList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(fView == null){
            FragmentGameListBinding fragmentBinding = FragmentGameListBinding.inflate((getLayoutInflater()));
            fView = fragmentBinding.getRoot();

            ((MainActivity)getActivity()).unCheckMenuitems();

            // Initialize recycler, adapter, gameList and progress bar onCreate.
            toolbar = fView.findViewById(R.id.gamelist_toolbar);
            toolbarTitle = fView.findViewById(R.id.genre_title);
            gameList = new ArrayList<>();
            progressBar = fView.findViewById(R.id.progressBar);
            loading = fView.findViewById(R.id.loading);

            recyclerView = fView.findViewById(R.id.recycleView);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            adapter = new GameListAdapter(mContext, gameList, "list");
            recyclerView.setAdapter(adapter);


            Log.e("est", getArguments().getString("chosenGenre"));

            String chosenGenre = getArguments().getString("chosenGenre");

            if (chosenGenre.equals("role"))
                toolbarTitle.setText("Role-Playing (RPG)");
            else
                toolbarTitle.setText(chosenGenre);


            //retrofit
            UserService userService = Api.getInstance().getUserService();

            // get games
            ApiCall(userService, chosenGenre);
        }

        return fView;
    }

    private void PutDataIntoRecyclerView(List<Game> gg) {
        adapter = new GameListAdapter(mContext, gg, "list");
        recyclerView.setAdapter(adapter);
    }

    private void ApiCall(UserService userService, String chosenGenre){
        Call<List<Game>> call = userService.getGames("name, cover.image_id, collection.name, screenshots.url, involved_companies.developer, involved_companies.company.name, expansions.name, genres.name, summary, aggregated_rating, url, videos.video_id, release_dates.human, release_dates.y, platforms.name, aggregated_rating_count, rating, rating_count; " +
                "where genres.name ~ \""+ chosenGenre +"\"* & first_release_date != null & cover != null; " +
                "limit 30;");

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                List<Game> games = response.body();

                for(Game game : games){
                    String ImageId = game.getCover().getImage_id();
                    game.setImage("https://images.igdb.com/igdb/image/upload/t_cover_big/"+ ImageId + ".png");
                    gameList.add(game);
                }
                PutDataIntoRecyclerView(gameList);
                progressBar.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                System.out.println("there was an error");
            }
        });
    }
}