package com.example.gamingnews.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.gamingnews.R;
import com.example.gamingnews.databinding.FragmentTabScreenshotsBinding;
import com.example.gamingnews.Server.Models.Game;
import com.example.gamingnews.Server.Models.Screenshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFragmentScreenshots#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragmentScreenshots extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //my variables
    private ViewPager2 screensViewPager;
    private Context mContext;

    public TabFragmentScreenshots() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragmentScreenshots.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFragmentScreenshots newInstance(String param1, String param2) {
        TabFragmentScreenshots fragment = new TabFragmentScreenshots();
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
        FragmentTabScreenshotsBinding fragmentBinding = FragmentTabScreenshotsBinding.inflate((getLayoutInflater()));
        View view = fragmentBinding.getRoot();


        Game game = getArguments().getParcelable("game");

        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        List<SlideModel> slideModels = new ArrayList<>();


        if(game.getScreenshots() != null){
            for (Screenshot screenshot : game.getScreenshots()){
                String screenUrl = "https://" + screenshot.getUrl().substring(2);
                String newUrl = screenUrl.replaceAll("t_thumb", "t_original");
                slideModels.add(new SlideModel(newUrl));
            }

            imageSlider.setImageList(slideModels, false);
        } else {
            imageSlider.setVisibility(View.INVISIBLE);
            view.findViewById(R.id.sadface_icon1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.sorry_txt1).setVisibility(View.VISIBLE);
        }
        

        return view;
    }
}