package com.example.gamingnews.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gamingnews.R;
import com.example.gamingnews.databinding.FragmentTabInfoBinding;
import com.example.gamingnews.Server.Models.Expansion;
import com.example.gamingnews.Server.Models.Game;
import com.example.gamingnews.Server.Models.InvolvedCompanies;
import com.example.gamingnews.Server.Models.Platform;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFragmentInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragmentInfo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // my variables
    private Context mContext;
    private TextView gameSummary;
    private TextView gamePlatforms;
    private TextView gameReleaseDate;
    private TextView gameDevelopers;
    private TextView gameExpansions;
    private TextView gameCollection;

    public TabFragmentInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragmentInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFragmentInfo newInstance(String param1, String param2) {
        TabFragmentInfo fragment = new TabFragmentInfo();
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
        FragmentTabInfoBinding fragmentBinding = FragmentTabInfoBinding.inflate((getLayoutInflater()));
        View view = fragmentBinding.getRoot();

        //Init variables
        gameSummary = view.findViewById(R.id.game_summary);
        gamePlatforms = view.findViewById(R.id.game_platforms);
        gameReleaseDate = view.findViewById(R.id.game_release_date);
        gameDevelopers = view.findViewById(R.id.game_developers);
        gameExpansions = view.findViewById(R.id.game_expansions);
        gameCollection = view.findViewById(R.id.game_collection);

        Game game = getArguments().getParcelable("game");
        String gameUrl = game.getUrl();


        gameSummary.setText(game.getSummary());
        gameReleaseDate.setText(game.getRelease_dates().get(0).getHuman());

        if(game.getPlatforms() != null){
            gamePlatforms.setText("");
            for (Platform platform: game.getPlatforms()){
                if (game.getPlatforms().indexOf(platform) == 0)
                    gamePlatforms.append(platform.getName());
                else
                    gamePlatforms.append(", " + platform.getName());
            }
        }

        if(game.getInvolved_companies() != null){
            gameDevelopers.setText("");
            for (InvolvedCompanies involvedCompanies: game.getInvolved_companies()){
                if(involvedCompanies.getDeveloper())
                    gameDevelopers.append(involvedCompanies.getCompany().getName());
            }
        }

        if(game.getExpansions() != null){
            gameExpansions.setText("");
            for (Expansion expansion : game.getExpansions()){
                if(game.getExpansions().indexOf(expansion) == 0)
                    gameExpansions.append(expansion.getName());
                else
                    gameExpansions.append(",\n" + expansion.getName());
            }
        } else view.findViewById(R.id.expansions).setVisibility(View.GONE);

        if(game.getCollection() != null){
            gameCollection.setText("");
            gameCollection.append(game.getCollection().getName());
        } else view.findViewById(R.id.collection).setVisibility(view.GONE);

        fragmentBinding.moreinfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(gameUrl));
                mContext.startActivity(myIntent);
            }
        });

        return view;
    }
}