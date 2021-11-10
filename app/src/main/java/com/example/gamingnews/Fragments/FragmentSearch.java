package com.example.gamingnews.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.gamingnews.R;
import com.example.gamingnews.databinding.FragmentSearchBinding;
import com.example.gamingnews.Adapters.GameListAdapter;
import com.example.gamingnews.Server.Models.Game;
import com.example.gamingnews.Server.Services.Api;
import com.example.gamingnews.Server.Services.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment {

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
    private View fView;
    private ProgressBar progressBar;

    public FragmentSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSearch newInstance(String param1, String param2) {
        FragmentSearch fragment = new FragmentSearch();
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
            FragmentSearchBinding fragmentBinding = FragmentSearchBinding.inflate((getLayoutInflater()));
            fView = fragmentBinding.getRoot();

            //init

            gameList = new ArrayList<>();
            recyclerView = fView.findViewById(R.id.search_recycler);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            adapter = new GameListAdapter(mContext, gameList, "search");
            recyclerView.setAdapter(adapter);
            progressBar = fView.findViewById(R.id.progressBarSearch);

            // retrofit
            UserService userService = Api.getInstance().getUserService();

            // SearchView + api call
            SetupSearchView(fView, userService);
        }

        return fView;
    }

    private void SetupSearchView(View view, UserService userService) {
        final SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                gameList.clear();
                progressBar.setVisibility(View.VISIBLE);
                Log.e("userinput", query);
                Call<List<Game>> call = userService.getGames("name, cover.image_id, collection.name, screenshots.url, involved_companies.developer, expansions.name, genres.name, summary, aggregated_rating, url, videos.video_id, release_dates.human, release_dates.y, platforms.name, aggregated_rating_count, rating, rating_count, involved_companies.company.name; limit 15; where cover != null & genres.name != null; search \""+ query +"\"");
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
                        progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(Call<List<Game>> call, Throwable t) {
                        System.out.println("there was an error");
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void PutDataIntoRecyclerView(List<Game> gg) {
        adapter = new GameListAdapter(mContext, gg, "search");
        recyclerView.setAdapter(adapter);
    }
}