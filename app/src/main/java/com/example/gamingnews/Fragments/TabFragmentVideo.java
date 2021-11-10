package com.example.gamingnews.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.gamingnews.R;
import com.example.gamingnews.databinding.FragmentTabVideoBinding;
import com.example.gamingnews.Server.Models.Game;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFragmentVideo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragmentVideo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //my variables
    YouTubePlayerView youTubePlayerView;
    Context mContext;

    public TabFragmentVideo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragmentVideo.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFragmentVideo newInstance(String param1, String param2) {
        TabFragmentVideo fragment = new TabFragmentVideo();
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
        FragmentTabVideoBinding fragmentBinding = FragmentTabVideoBinding.inflate((getLayoutInflater()));
        View view = fragmentBinding.getRoot();



        //init youtubeplayer, game
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        youTubePlayerView.setVisibility(View.INVISIBLE);
        getLifecycle().addObserver(youTubePlayerView);
        Game game = getArguments().getParcelable("game");

        //set search video on youtube listener
        fragmentBinding.youtubeSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + game.getName()));
                mContext.startActivity(myIntent);
            }
        });


        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            int count = 0;
            @Override
            public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {
                if (count == 0) {
                    count++;
                    if (game.getVideos() != null){
                        youTubePlayer.loadVideo(game.getVideos().get(0).getVideo_id(), 0);
                        youTubePlayerView.setVisibility(View.VISIBLE);
                    }
                    else {
                        youTubePlayer.pause();
                        view.findViewById(R.id.sadface_icon).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.sorry_txt).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.youtube_search_btn).setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return view;
    }
}