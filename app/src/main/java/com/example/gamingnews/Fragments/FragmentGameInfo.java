package com.example.gamingnews.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamingnews.Adapters.FragmentAdapter;
import com.example.gamingnews.Utils.BlurTransformation;
import com.example.gamingnews.R;
import com.example.gamingnews.databinding.FragmentGameInfoBinding;
import com.example.gamingnews.Server.Models.Game;
import com.example.gamingnews.Server.Models.Genre;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGameInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGameInfo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //My variables
    private Context mContext;
    private ImageView gameImgBg;
    private ImageView gameImgCover;
    private TextView gameName;
    private TextView gameReleaseYear;
    private TextView gameGenre;
    private TextView gameRating;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapter pagerAdapter;
    private Bundle mBundle = new Bundle();

    public FragmentGameInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGameInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGameInfo newInstance(String param1, String param2) {
        FragmentGameInfo fragment = new FragmentGameInfo();
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
        FragmentGameInfoBinding fragmentBinding = FragmentGameInfoBinding.inflate((getLayoutInflater()));
        View view = fragmentBinding.getRoot();

        Game game = getArguments().getParcelable("game");

        // log if game has video
        if (game.getVideos() != null)
            Log.e("videoID", game.getVideos().get(0).getVideo_id());
        else Log.e("videoID", "empty");



        //Init variables
        gameImgBg = view.findViewById(R.id.game_bg_info);
        gameImgCover = view.findViewById(R.id.game_cover_img_info);
        gameRating = view.findViewById(R.id.userrating_info);
        gameReleaseYear = view.findViewById(R.id.game_releaseyear);
        gameGenre = view.findViewById(R.id.game_genre_info);
        gameName = view.findViewById(R.id.game_name_info);
        gameName.setText(game.getName());
        gameReleaseYear.setText(Integer.toString(game.getRelease_dates().get(0).getY()));

        tabLayout = view.findViewById(R.id.tab_layout_1);
        viewPager2 = view.findViewById(R.id.view_pagerown);


        //Init bundle for video,info and screenshots fragments, also init viewpager.
        createBundle();
        InitViewPager();


        if (game.getRating() != 0)
            gameRating.setText(String.format("%.0f", game.getRating()));


        gameGenre.setText("");
        if (game.getGenres() != null){
            for (Genre genre: game.getGenres()){
                if (game.getGenres().indexOf(genre) == 0)
                    gameGenre.append(genre.getName());
                else
                    gameGenre.append(", " + genre.getName());
            }
        } else
            gameGenre.setText("N/A");


        //set images
        String gameImage = game.getImage();

        Picasso.get()
                .load(gameImage)
                .transform(new BlurTransformation(mContext))
                .into(gameImgBg);

        Picasso.get()
                .load(gameImage)
                .into(gameImgCover);

        return view;
    }

    private void createBundle (){
        mBundle.putParcelable("game", getArguments().getParcelable("game"));
    }

    private void InitViewPager(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        pagerAdapter = new FragmentAdapter(fragmentManager, getLifecycle(), mBundle);
        viewPager2.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}