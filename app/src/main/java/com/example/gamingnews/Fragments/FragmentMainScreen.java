package com.example.gamingnews.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gamingnews.R;
import com.example.gamingnews.Adapters.LatestGamesAdapter;
import com.example.gamingnews.databinding.FragmentMainScreenBinding;
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
 * Use the {@link FragmentMainScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMainScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //my variables
    private Context mContext;
    private List<Game> gameList;
    private ViewPager2 gamesViewPager;
    private ProgressBar progressBar;
    private View fView;

    public FragmentMainScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMainScreen newInstance(String param1, String param2) {
        FragmentMainScreen fragment = new FragmentMainScreen();
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
        if (fView == null){
            FragmentMainScreenBinding fragmentBinding = FragmentMainScreenBinding.inflate((getLayoutInflater()));
            fView = fragmentBinding.getRoot();

            // Initialize viewpager and gameList onCreate.
            gameList = new ArrayList<>();
            progressBar = fView.findViewById(R.id.progressBarRecent);
            gamesViewPager = fView.findViewById(R.id.viewPager);
            gamesViewPager.setAdapter(new LatestGamesAdapter(gameList, mContext));


            // genre buttons listeners
            fView.findViewById(R.id.strategy_card).setOnClickListener(mListener);
            fView.findViewById(R.id.puzzle_card).setOnClickListener(mListener);
            fView.findViewById(R.id.racing_card).setOnClickListener(mListener);
            fView.findViewById(R.id.rpg_card).setOnClickListener(mListener);
            fView.findViewById(R.id.shooter_card).setOnClickListener(mListener);
            fView.findViewById(R.id.sport_card).setOnClickListener(mListener);
            fView.findViewById(R.id.adventure_btn).setOnClickListener(mListener);
            fView.findViewById(R.id.moba_btn).setOnClickListener(mListener);

            //unix timestamp
            long unixTime = System.currentTimeMillis() / 1000L;


            // retro fit
            UserService userService = Api.getInstance().getUserService();

            // get games
            ApiCall(userService, unixTime);

        }
        return fView;
    }

    private void PutDataIntoViewPager(List<Game> gg){
        gamesViewPager.setClipToPadding(false);
        gamesViewPager.setClipChildren(false);
        gamesViewPager.setOffscreenPageLimit(3);
        gamesViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f+ r * 0.15f);
        });
        gamesViewPager.setPageTransformer(compositePageTransformer);
        gamesViewPager.setAdapter(new LatestGamesAdapter(gg, mContext));

    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            switch (v.getId()){
                case R.id.shooter_card:
                    bundle.putString("chosenGenre", "Shooter");
                    break;
                case R.id.racing_card:
                    bundle.putString("chosenGenre", "Racing");
                    break;
                case R.id.strategy_card:
                    bundle.putString("chosenGenre", "Strategy");
                    break;
                case R.id.sport_card:
                    bundle.putString("chosenGenre", "Sport");
                    break;
                case R.id.rpg_card:
                    bundle.putString("chosenGenre", "role");
                    break;
                case R.id.puzzle_card:
                    bundle.putString("chosenGenre", "Puzzle");
                    break;
                case R.id.moba_btn:
                    bundle.putString("chosenGenre", "MOBA");
                    break;
                case R.id.adventure_btn:
                    bundle.putString("chosenGenre", "Adventure");
                    break;
                default:
                    bundle.putString("chosenGenre", "genre not found");
            }
            Navigation.findNavController(v).navigate(R.id.action_blankFragment_to_fragmentGameList, bundle);
        }
    };

    private void ApiCall(UserService userService, long unixTime){
        Call<List<Game>> call = userService.getGames("name, collection.name, cover.image_id, expansions.name, involved_companies.developer, genres.name, summary, aggregated_rating, url, videos.video_id, release_dates.human, release_dates.y, platforms.name, aggregated_rating_count, rating, rating_count, involved_companies.company.name;" +
                " where first_release_date != null & first_release_date < " + unixTime + " & cover != null & genres.name != null;" +
                " sort first_release_date desc;" +
                " limit 12;");

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                List<Game> games = response.body();

                for (Game game : games) {
                    String ImageId = game.getCover().getImage_id();
                    game.setImage("https://images.igdb.com/igdb/image/upload/t_cover_big/"+ ImageId + ".png");
                    gameList.add(game);
                }
                PutDataIntoViewPager(gameList);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                System.out.println("there was an error");
            }
        });

    }
}